;
; Copyright © 2012 Peter Monks (pmonks@gmail.com)
;
; This work is licensed under the Creative Commons Attribution-ShareAlike 3.0
; Unported License. To view a copy of this license, visit
; http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative
; Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
;

(ns letterpress-solver.solver
  (:gen-class))

; ---- Naive implementation (slowest)

(def ^:private frequencies-memo (memoize frequencies))

(defn- word-letter-frequency
  "Returns the number of occurrences of the letter in the word."
  [word letter]
  (let [result (get (frequencies-memo word) letter)]
    (if (nil? result)
      0
      result)))

(def ^:private word-letter-frequency-memo (memoize word-letter-frequency))

(defn- contains-letter-sufficient-times?
  "Does the letter appear in the string of characters at least as many times as it appears in the word?"
  [characters word letter]
  (<= (word-letter-frequency word letter) (word-letter-frequency-memo characters letter)))

(defn- contains-word?
  "Can the word be constructed from the string of characters?"
  [characters word]
  (every? identity (map #(contains-letter-sufficient-times? characters word %) word)))

(defn matching-words
  "Returns all of the words that can be made from the string of characters.
  e.g. (matching-words \"abcd\" [\"bad\" \"good\"]) -> \"bad\""
  [characters words]
  (sort-by #(conj [] (- (count %)) %) (filter #(contains-word? characters %) words)))

; ---- Pre-calculated frequencies for characters (fastest)

; If any word in the source dictionary has a character not in this map, the solver will blow up with an NPE.
(def ^:private empty-alphabet { \A 0, \B 0, \C 0, \D 0, \E 0, \F 0, \G 0, \H 0, \I 0, \J 0, \K 0, \L 0, \M 0, \N 0, \O 0, \P 0, \Q 0, \R 0, \S 0, \T 0, \U 0, \V 0, \W 0, \X 0, \Y 0, \Z 0,
                                \a 0, \b 0, \c 0, \d 0, \e 0, \f 0, \g 0, \h 0, \i 0, \j 0, \k 0, \l 0, \m 0, \n 0, \o 0, \p 0, \q 0, \r 0, \s 0, \t 0, \u 0, \v 0, \w 0, \x 0, \y 0, \z 0,
                                \0 0, \1 0, \2 0, \3 0, \4 0, \5 0, \6 0, \7 0, \8 0, \9 0,
                                \- 0 })

(defn- contains-letter-sufficient-times2?
  "Does the letter appear in the string of characters at least as many times as it appears in the word?"
  [characters-frequencies word-frequencies letter]
  (<= (get word-frequencies letter) (get characters-frequencies letter)))

(defn- contains-word2?
  "Can the word be constructed from the string of characters represented by characters-alphabet-frequencies?"
  [characters-frequencies word]
  (let [word-frequencies (frequencies word)]
    (every? identity (map #(contains-letter-sufficient-times2? characters-frequencies word-frequencies %) word))))

(defn matching-words2
  "Returns all of the words that can be made from the string of characters.
  e.g. (matching-words \"abcd\" [\"bad\" \"good\"]) -> \"bad\""
  [characters words]
  (let [characters-frequencies (merge empty-alphabet (frequencies characters))]
    (sort-by #(conj [] (- (count %)) %) (filter #(contains-word2? characters-frequencies %) words))))

; ---- Anagram based version of previous - not sure why this is slower!

(defn anagrams
  "Groups the words into anagrams."
  [words]
  (group-by #(apply str (sort %)) words))

(defn matching-words-from-anagrams
  "Returns all of the words in the anagram list that can be made from the string of characters."
  [characters anagrams]
  (let [characters-frequencies (merge empty-alphabet (frequencies characters))]
    (sort-by #(conj [] (- (count %)) %) (mapcat #(get anagrams %) (filter #(contains-word2? characters-frequencies %) (keys anagrams))))))

