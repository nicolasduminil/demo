package fr.dsirc.demo.repo;

import fr.dsirc.demo.jpa.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface IndividualRepository extends JpaRepository<IndividualEntity, Long>
{
  Optional<IndividualEntity> findByRef(String ref);
}
