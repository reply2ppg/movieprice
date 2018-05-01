package com.ppg.services.movieprice.repository;

import com.ppg.services.movieprice.entity.Price;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class PriceRepository implements IPriceRepository {
    private final JdbcTemplate jdbcTemplate;

    public PriceRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Price> findMoviePrices(Triple<String, String, String> searchCriteria) {
        String movie = searchCriteria.getLeft();
        String theater = searchCriteria.getMiddle();
        String city = searchCriteria.getRight();

        StringBuilder builder = new StringBuilder();
        builder.append("Select p.priceId as priceId, m.name as movieName, t.name theaterName, t.city cityName, p.price as price from ");
        builder.append("price p, movie m, theater t ");
        builder.append("where p.movieId = m.movieId ");
        builder.append("and p.theaterId = t.theaterId ");
        if(StringUtils.isNotBlank(movie)) {
            builder.append("and upper(m.name) like '" + movie.toUpperCase() +"%' ");
        }
        if(StringUtils.isNotBlank(theater)) {
            builder.append("and upper(t.name) like '" + theater.toUpperCase() +"%' ");
        }
        if(StringUtils.isNotBlank(city)) {
            builder.append("and upper(t.city) like '" + city.toUpperCase() +"%' ");
        }
        builder.append("order by p.priceId asc ");

        List<Price> prices = jdbcTemplate.query(builder.toString(), new BeanPropertyRowMapper<>(Price.class));
        return prices;
    }

    @Override
    public Integer insertPrice(Integer movieId, Integer theaterId, Double price) {
        if(null == movieId || null == theaterId){
            return null;
        }
        String insertSql = "INSERT INTO price(movieId, theaterId, price) VALUES (?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
                                @Override
                                public PreparedStatement createPreparedStatement(Connection con) throws SQLException {

                                    PreparedStatement ps = con.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                                    ps.setInt(1, movieId);
                                    ps.setInt(2, theaterId);
                                    ps.setDouble(3, null != price? price : new Double(0.0));
                                    return ps;
                                }
                            }, holder);
        return holder.getKey().intValue();

    }

}
