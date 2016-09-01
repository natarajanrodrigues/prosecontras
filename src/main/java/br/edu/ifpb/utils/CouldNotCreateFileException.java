/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.utils;

/**
 *
 * @author kieckegard
 */
public class CouldNotCreateFileException extends RuntimeException {
    public CouldNotCreateFileException(String msg) {
        super(msg);
    }
}
