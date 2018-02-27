package de.informatikwerk.ash.repository;

import de.informatikwerk.ash.domain.Gui;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Gui entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GuiRepository extends JpaRepository<Gui, Long> {

}
