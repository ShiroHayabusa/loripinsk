package com.loripin.auto.repos;

import com.loripin.auto.model.Modification;
import com.loripin.auto.model.Spot;
import com.loripin.auto.model.User;
import com.loripin.auto.model.dto.SpotDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpotRepo extends JpaRepository<Spot, Long> {

    @Query("select new com.loripin.auto.model.dto.SpotDto(" +
            "    m, " +
            "    count(ml), " +
            "    sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Spot m left join m.likes ml " +
            "group by m")
    List<SpotDto> findAllByOrderByIdDesc(@Param("user") User user);

    @Query("select new com.loripin.auto.model.dto.SpotDto(" +
            "    m, " +
            "    count(ml), " +
            "    sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Spot m left join m.likes ml " +
            "where m.modification = :modification " +
            "group by m")
    List<SpotDto> findByModificationIdOrderByIdDesc(@Param("modification") Modification modification,
                                                 @Param("user") User user);

    List<Spot> findByModificationOrderByIdDesc(Modification modification);

    @Query("select new com.loripin.auto.model.dto.SpotDto(" +
            "    m, " +
            "    count(ml), " +
            "    sum(case when ml = :user then 1 else 0 end) > 0" +
            ") " +
            "from Spot m left join m.likes ml " +
            "where m.user = :user1 " +
            "group by m")
    List<SpotDto> findByUserIdOrderByIdDesc(@Param("user") User user1, @Param("user1") User user);
}
