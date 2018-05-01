package com.ppg.services.movieprice.entity;

public class Price {
    private Integer priceId;
    private String movieName;
    private String theaterName;
    private String cityName;
    private Double price;

    @java.beans.ConstructorProperties({"priceId", "movieName", "theaterName", "cityName", "price"})
    public Price(Integer priceId, String movieName, String theaterName, String cityName, Double price) {
        this.priceId = priceId;
        this.movieName = movieName;
        this.theaterName = theaterName;
        this.cityName = cityName;
        this.price = price;
    }

    public Price() {
    }

    public static PriceBuilder builder() {
        return new PriceBuilder();
    }

    public Integer getPriceId() {
        return this.priceId;
    }

    public String getMovieName() {
        return this.movieName;
    }

    public String getTheaterName() {
        return this.theaterName;
    }

    public String getCityName() {
        return this.cityName;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Price)) return false;
        final Price other = (Price) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$priceId = this.getPriceId();
        final Object other$priceId = other.getPriceId();
        if (this$priceId == null ? other$priceId != null : !this$priceId.equals(other$priceId)) return false;
        final Object this$movieName = this.getMovieName();
        final Object other$movieName = other.getMovieName();
        if (this$movieName == null ? other$movieName != null : !this$movieName.equals(other$movieName)) return false;
        final Object this$theaterName = this.getTheaterName();
        final Object other$theaterName = other.getTheaterName();
        if (this$theaterName == null ? other$theaterName != null : !this$theaterName.equals(other$theaterName))
            return false;
        final Object this$cityName = this.getCityName();
        final Object other$cityName = other.getCityName();
        if (this$cityName == null ? other$cityName != null : !this$cityName.equals(other$cityName)) return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $priceId = this.getPriceId();
        result = result * PRIME + ($priceId == null ? 43 : $priceId.hashCode());
        final Object $movieName = this.getMovieName();
        result = result * PRIME + ($movieName == null ? 43 : $movieName.hashCode());
        final Object $theaterName = this.getTheaterName();
        result = result * PRIME + ($theaterName == null ? 43 : $theaterName.hashCode());
        final Object $cityName = this.getCityName();
        result = result * PRIME + ($cityName == null ? 43 : $cityName.hashCode());
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Price;
    }

    public String toString() {
        return "Price(priceId=" + this.getPriceId() + ", movieName=" + this.getMovieName() + ", theaterName=" + this.getTheaterName() + ", cityName=" + this.getCityName() + ", price=" + this.getPrice() + ")";
    }

    public PriceBuilder toBuilder() {
        return new PriceBuilder().priceId(this.priceId).movieName(this.movieName).theaterName(this.theaterName).cityName(this.cityName).price(this.price);
    }

    public static class PriceBuilder {
        private Integer priceId;
        private String movieName;
        private String theaterName;
        private String cityName;
        private Double price;

        PriceBuilder() {
        }

        public Price.PriceBuilder priceId(Integer priceId) {
            this.priceId = priceId;
            return this;
        }

        public Price.PriceBuilder movieName(String movieName) {
            this.movieName = movieName;
            return this;
        }

        public Price.PriceBuilder theaterName(String theaterName) {
            this.theaterName = theaterName;
            return this;
        }

        public Price.PriceBuilder cityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public Price.PriceBuilder price(Double price) {
            this.price = price;
            return this;
        }

        public Price build() {
            return new Price(priceId, movieName, theaterName, cityName, price);
        }

        public String toString() {
            return "Price.PriceBuilder(priceId=" + this.priceId + ", movieName=" + this.movieName + ", theaterName=" + this.theaterName + ", cityName=" + this.cityName + ", price=" + this.price + ")";
        }
    }
}
