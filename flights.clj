(defn ffrom [from flights]
  (filter (fn [f] (= from (f :from))) flights))

(defn fnfrom [from flights]
  (filter (fn [f] (not (= from (f :from)))) flights))

(defn route [to other af]
  (let [n (itinerary (af :to) to other)]
    (if (empty? n)
      (if (= (af :to) to)
        af
        nil)
      {:from (af :from) :to n})))

(defn itinerary [from to flights]
  (if (= from to)
    nil
    (if (empty? flights)
      nil
      (let [avail (ffrom from flights) other (fnfrom from flights)]
        (filter identity (map (partial route to other) avail))))))

(itinerary :lax :phl [{:from :lax :to :phl}])

(itinerary :lax :phl [{:from :lax :to :jfk} {:from :jfk :to :phl}])

(itinerary :lax :phl [{:from :lax :to :jfk} {:from :jfk :to :chi} {:from :chi :to :phl}])

(itinerary :lax :phl [{:from :lax :to :jfk} {:from :jfk :to :phl} {:from :jfk :to :chi}])