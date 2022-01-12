(ns advent2021.util)

(defn lopr [s t]
  (if (clojure.string/ends-with? s t)
    (subs s 0 (- (count s) (count t)))
    s))

(defmacro publicise [sym]
  `(var ~(symbol (str (lopr (str *ns*) "-test") "/" sym))))
