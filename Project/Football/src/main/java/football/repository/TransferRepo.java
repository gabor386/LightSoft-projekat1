package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Transfer;

public interface TransferRepo extends JpaRepository<Transfer, Integer>{

}
