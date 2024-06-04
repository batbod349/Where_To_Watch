package com.example.where_to_watch.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountryWatchProviders {
        @SerializedName("link")
        private String link;

        @SerializedName("flatrate")
        private List<WatchProviders> flatrate;

        @SerializedName("buy")
        private List<WatchProviders> buy;

        @SerializedName("rent")
        private List<WatchProviders> rent;

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public List<WatchProviders> getFlatrate() {
            return flatrate;
        }

        public void setFlatrate(List<WatchProviders> flatrate) {
            this.flatrate = flatrate;
        }

        public List<WatchProviders> getBuy() {
            return buy;
        }

        public void setBuy(List<WatchProviders> buy) {
            this.buy = buy;
        }

        public List<WatchProviders> getRent() {
            return rent;
        }

        public void setRent(List<WatchProviders> rent) {
            this.rent = rent;
        }
}
