package tn.esprit.autoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IEmployeeRepository;
import tn.esprit.autoloc.service.IEmployeService;

@Service
@RequiredArgsConstructor
public class EmployeServiceImpl implements IEmployeService {

    private final IEmployeeRepository employeRepository;

    @Override
    public Employe create(Employe employe) {
        if (employe.getId() != null) {
            throw new IllegalArgumentException(
                    "Un nouvel employé ne doit pas avoir d'identifiant");
        }
        verifier(employe);
        return employeRepository.save(employe);
    }

    @Override
    public Employe findById(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employe", id));
    }

    @Override
    public List<Employe> findAll() {
        return employeRepository.findAll();
    }

    @Override
    public Employe update(Long id, Employe employe) {
        verifier(employe);
        Employe existant = findById(id);
        existant.setNom(employe.getNom());
        existant.setPrenom(employe.getPrenom());
        existant.setRole(employe.getRole());
        // l'agence (association) n'est pas recopiée : Chapitre 9
        return employeRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        if (!employeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employe", id);
        }
        employeRepository.deleteById(id);
    }

    private void verifier(Employe e) {
        if (estVide(e.getNom()) || estVide(e.getPrenom())) {
            throw new IllegalArgumentException("Le nom et le prénom de l'employé sont obligatoires");
        }
        if (e.getRole() == null) {
            throw new IllegalArgumentException("Le rôle de l'employé est obligatoire");
        }
    }

    private boolean estVide(String valeur) {
        return valeur == null || valeur.isBlank();
    }
}