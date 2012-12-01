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

(defn- get-with-default
  "A safe version of get that returns a default-value when the key doesn't exist in the map."
  [map key default-value]
  (let [result (get map key)]
    (if (nil? result)
      default-value
      result)))

(defn- contains-letter-sufficient-times2?
  "Does the letter appear in the string of characters at least as many times as it appears in the word?"
  [character-frequencies word-frequencies letter]
  (<= (get word-frequencies letter) (get-with-default character-frequencies letter 0)))

(defn- contains-word2?
  "Can the word be constructed from the string of characters represented by characters-alphabet-frequencies?"
  [character-frequencies word]
  (let [word-frequencies (frequencies word)]
    (every? identity (map #(contains-letter-sufficient-times2? character-frequencies word-frequencies %) word))))

(defn matching-words2
  "Returns all of the words that can be made from the string of characters.
  e.g. (matching-words \"abcd\" [\"bad\" \"good\"]) -> \"bad\""
  [characters words]
  (let [character-frequencies (frequencies characters)]
    (sort-by #(conj [] (- (count %)) %) (filter #(contains-word2? character-frequencies %) words))))

; ---- Anagram based version of previous - not sure why this is slower!

(defn anagrams
  "Groups the words into anagrams."
  [words]
  (group-by #(apply str (sort %)) words))

(defn matching-words-from-anagrams
  "Returns all of the words in the anagram list that can be made from the string of characters."
  [characters anagrams]
  (let [character-frequencies (frequencies characters)]
    (sort-by #(conj [] (- (count %)) %) (mapcat #(get anagrams %) (filter #(contains-word2? character-frequencies %) (keys anagrams))))))

