;
; Copyright © 2012-2013 Peter Monks (pmonks@gmail.com)
;
; This work is licensed under the Creative Commons Attribution-ShareAlike 3.0
; Unported License. To view a copy of this license, visit
; http://creativecommons.org/licenses/by-sa/3.0/ or send a letter to Creative
; Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
;

(ns letterpress-solver.solver
  (:gen-class))

(defn- get-with-default
  "A version of get that returns a default-value when the key doesn't exist in the map."
  [the-map key default-value]
  (let [result (get the-map key)]
    (if (nil? result)
      default-value
      result)))

(defn- contains-letter-sufficient-times?
  "Does the letter appear in the string of characters at least as many times as it appears in the word?"
  [character-frequencies word-frequencies letter]
  (<= (get-with-default word-frequencies letter 0) (get-with-default character-frequencies letter 0)))

(defn- contains-word?
  "Can the word be constructed from the string of characters represented by characters-frequencies?"
  [character-frequencies word]
  (let [word-frequencies (frequencies word)]
    (every? identity (map #(contains-letter-sufficient-times? character-frequencies word-frequencies %) word))))

(defn matching-words
  "Returns all of the words from the dictionary that can be made from the string of characters, reverse sorted by length.
  e.g. (matching-words \"abcd\" [\"bad\" \"good\"]) -> [\"bad\"]"
  [characters dictionary]
  (let [character-frequencies (frequencies characters)]
    (sort-by #(conj [] (- (count %)) %) (filter #(contains-word? character-frequencies %) dictionary))))

(defn- contains-letters?
  "Does the word contain all of the given letters, with at least those frequencies?"
  [characters word]
  (let [character-frequencies (frequencies characters)
        word-frequencies      (frequencies word)]
    (every? identity (map #(contains-letter-sufficient-times? word-frequencies character-frequencies %) characters))))

(defn words-containing
  "Returns all words from the dictionary that contain the given characters, reverse sorted by length.
  e.g. (words-containing \"ae\" [\"apple\" \"blueberry\"]) -> [\"apple\"]"
  [characters dictionary]
  (sort-by #(conj [] (- (count %)) %) (filter #(contains-letters? characters %) dictionary)))

; ---- Anagram based version of previous - not sure why this is slower!

(defn anagrams
  "Groups the words into anagrams."
  [dictionary]
  (group-by #(apply str (sort %)) dictionary))

(defn matching-words-from-anagrams
  "Returns all of the words in the anagram list that can be made from the string of characters."
  [characters anagrams]
  (let [character-frequencies (frequencies characters)]
    (sort-by #(conj [] (- (count %)) %) (mapcat #(get anagrams %) (filter #(contains-word? character-frequencies %) (keys anagrams))))))

