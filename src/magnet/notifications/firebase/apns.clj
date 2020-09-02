(ns magnet.notifications.firebase.apns
  (:require [clojure.walk :as walk])
  (:import [com.google.firebase.messaging ApnsConfig Aps]))

(defn- payload->Aps [{:keys [content-available]}]
  (cond-> (Aps/builder)
    content-available (.setContentAvailable content-available)
    :always (.build)))

(defn apns-options->ApnsConfig
  [{:keys [headers payload]}]
  (cond-> (ApnsConfig/builder)
    headers (.putAllHeaders (walk/stringify-keys headers))
    :always (.setAps (payload->Aps payload))
    :always (.build)))
