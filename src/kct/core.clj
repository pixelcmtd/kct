(ns kct.core (:gen-class))

(defn str-slide [keynote i]
  (concat
   [(str "\033[1;1H\033[J " (keynote :title) "(" (inc i) "/" (count (keynote :slides)) ")") ""]
   (nth (keynote :slides) i)
   ["" (str " " (keynote :ad))]))

(defn kct-loop [keynote i]
  (if (= i (count (keynote :slides))) (System/exit 0) nil)
  (doall (map println (str-slide keynote i)))
  (kct-loop keynote
    (let [l (read-line) p (try (Integer/parseInt l) (catch Exception e nil))]
      (cond
        (not (nil? p)) (dec p)
        (= l "")       (inc i)
        (= l "n")      (inc i)
        (= l "p")      (dec i)
        (= l "h")      (dec i)
        (= l "j")      (inc i)
        (= l "k")      (dec i)
        (= l "l")      (inc i)
        (= l "q")      (System/exit 0)
        :else i))))

(defn -main [& args]
 (if (= (count args) 0) (System/exit 1)
  (kct-loop
   (clojure.edn/read
    (java.io.PushbackReader.
     (clojure.java.io/reader
      (nth args 0))))
   0)))
