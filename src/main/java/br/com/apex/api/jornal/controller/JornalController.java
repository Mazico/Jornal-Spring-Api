package br.com.apex.api.jornal.controller;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apex.api.jornal.exception.JornalException;
import br.com.apex.api.jornal.model.JornalModel;
import br.com.apex.api.jornal.repository.JornalRepository;

@RestController
@RequestMapping("/jornal")
public class JornalController {
	
	@Autowired
	private JornalRepository jornalRepository;
	
	
	@GetMapping
	public List<JornalModel> listar(){
		return jornalRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<JornalModel> buscar(@PathVariable Integer id){
			Optional<JornalModel> jornalModel = jornalRepository.findById(id);
			
			if(jornalModel.isPresent()) {
				return ResponseEntity.ok(jornalModel.get());
			}
			
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public JornalModel postar (@RequestBody JornalModel jornalModel) {
		jornalModel.setData(OffsetDateTime.now());
		return jornalRepository.save(jornalModel);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<JornalModel> atualizar(@PathVariable Integer id,
			@RequestBody JornalModel jornalModel) throws JornalException{
		JornalModel noticiaAtualizada = jornalRepository.findById(id).orElseThrow(()
				-> new JornalException("Noticia com a id " +id+ " não encontrada"));
		
		noticiaAtualizada.setAutor(jornalModel.getAutor());
		noticiaAtualizada.setConteudoNoticia(jornalModel.getConteudoNoticia());
		noticiaAtualizada.setAutor(jornalModel.getTitulo());
		noticiaAtualizada.setData(OffsetDateTime.now());
		
		JornalModel novaNoticia = jornalRepository.save(noticiaAtualizada);
		return ResponseEntity.ok(novaNoticia);
	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Integer id){
		
		if(!jornalRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		jornalRepository.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
}

