package com.example.hospital.service;

import com.example.hospital.model.Patient;
import com.example.hospital.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // CREATE a new patient
    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    // UPDATE patient details
    public Patient updatePatient(Long id, Patient newPatient) {
        return patientRepository.findById(id)
                .map(existingPatient -> {
                    existingPatient.setName(newPatient.getName());
                    existingPatient.setAge(newPatient.getAge());
                    existingPatient.setGender(newPatient.getGender());
                    existingPatient.setDisease(newPatient.getDisease());
                    return patientRepository.save(existingPatient);
                }).orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    // DELETE a patient
    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    // FIND patient by ID
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    // GET all patients
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
}
