package com.esprit.cloudcraft.repository.userDao;

import com.esprit.cloudcraft.entities.Participation;
import com.esprit.cloudcraft.entities.userEntities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
  Optional<User> findByEmail(String email);
//   user   findByUsername (String username);
Optional<User> findByTokens(List<String> tokens);
  @Query("SELECT MONTH(u.createdDate) AS month, COUNT(u) AS userCount " +
          "FROM User u " +
          "WHERE YEAR(u.createdDate) = YEAR(CURRENT_DATE()) " +
          "GROUP BY MONTH(u.createdDate) " )
  List<Object[]> countUsersByMonthJPQL();
  @Query(value = "SELECT MONTH(created_date) AS month, COUNT(*) AS userCount " +
          "FROM user WHERE YEAR(created_date) = YEAR(CURDATE()) GROUP BY MONTH(created_date) ORDER BY month", nativeQuery = true)
  List<Object[]> countUsersByMonthNativeQuery();
  @Query(value = "SELECT MONTH(Locked_date) AS month, COUNT(*) AS userCount " +
          "FROM user WHERE YEAR(Locked_date) = YEAR(CURDATE()) AND (not_locker)=0   AND ((u_locked_date) IS NULL OR (u_locked_date) = '') GROUP BY MONTH(Locked_date) ORDER BY month", nativeQuery = true)
  List<Object[]> countUserslockedByMonthNativeQuery();
  @Query(value = "SELECT (class_type) AS class, COUNT(*) AS userCount " +
          "FROM user  GROUP BY (class_type)", nativeQuery = true)
  List<Object[]> numberofusersbyClasstype();




  @Query(value = "SELECT DAYOFMONTH(created_at) AS day, COUNT(*) AS sessionCount " +
          "FROM token " +
          "WHERE YEAR(created_at) = YEAR(CURRENT_DATE()) AND MONTH(created_at) = MONTH(CURRENT_DATE()) " +
          "GROUP BY DAYOFMONTH(created_at) " +
          "ORDER BY day", nativeQuery = true)
  List<Object[]> countSessionsByDayCurrentYearAndMonth();
  @Query(value = "SELECT  COUNT(*) AS users " +
          "FROM user WHERE YEAR(created_date) = YEAR(CURDATE())"

          , nativeQuery = true)
  List<Long> numberofuserscurrentyear();
  @Query(value = "SELECT  COUNT(*) AS users " +
          "FROM user WHERE (enable)=0 "

          , nativeQuery = true)
  List<Long> numberofuserscurrentyeardisabled();


  @Query(value = "SELECT COUNT(*) FROM User u WHERE u.email NOT LIKE '%@%'", nativeQuery = true)
  Long countUsersWithEmailWithoutAtSymbol();

  List<User> getUserByParticipationsIn(List<Participation> participations);


}
