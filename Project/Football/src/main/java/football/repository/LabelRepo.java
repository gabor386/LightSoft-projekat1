package football.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import model.Label;

public interface LabelRepo extends JpaRepository<Label, Integer> {

}
