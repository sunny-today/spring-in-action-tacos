package tacos.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import tacos.Taco;

import java.awt.print.Pageable;


public interface TacoRepository extends JpaRepository<Taco, Long>{
    //PagingAndSortingRepository<Taco, Long> {

}
