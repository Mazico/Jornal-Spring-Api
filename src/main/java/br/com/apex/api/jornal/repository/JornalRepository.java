package br.com.apex.api.jornal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apex.api.jornal.model.JornalModel;

public interface JornalRepository extends JpaRepository<JornalModel, Integer> {

}
