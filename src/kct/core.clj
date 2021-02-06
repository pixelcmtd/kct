(ns kct.core
  (:gen-class))

(defn print-slide [keynote i]
  (print "\033[1;1H\033[J")
  (println "" (get keynote :title) (str "(" (inc i) "/" (count (get keynote :slides)) ")"))
  (println)
  (doall (map println (nth (get keynote :slides) i)))
  (println)
  (println "" (get keynote :ad)))

(defn kct-loop [keynote i]
  (print-slide keynote i)
  (kct-loop keynote
    (let [l (read-line)]
      (cond
        (= l "n") (inc i)
        (= l "p") (dec i)
        (= l "h") (dec i)
        (= l "j") (inc i)
        (= l "k") (dec i)
        (= l "l") (inc i)
        (= l "q") (System/exit 0)
        :else i))))

(defn -main
  "Starts KCT."
  [& args]
  (kct-loop
    (clojure.edn/read
      (java.io.PushbackReader.
        (clojure.java.io/reader (nth args 0))))
    0))
