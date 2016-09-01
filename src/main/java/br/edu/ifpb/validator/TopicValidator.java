/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.validator;

import javax.validation.constraints.NotNull;

/**
 *
 * @author kieckegard
 */
public class TopicValidator {
    
    @NotNull(message = "Por favor, insira o nome do tópico!")
    private String name;
    @NotNull(message = "Por favor, insira uma descrição!")
    private String description;

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
