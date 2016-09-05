/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.validator;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author kieckegard
 */
public class TopicValidator {

    @Size(min = 1, max = 150, message = "Não pode ser nulo. Valor máximo - 150 caracteres")
    @NotNull(message = "Por favor, insira o nome do tópico!")
    private String name;

    @Size(min = 1, max = 1500, message = "Não pode ser nulo. Valor máximo - 1500 caracteres")
    @NotNull(message = "Por favor, insira uma descrição!")
    private String description;

    public MultipartFile file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
