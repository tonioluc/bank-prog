/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.entreprise.remote;

import com.entreprise.dto.DirectionDTO;
import jakarta.ejb.Remote;
import java.util.List;

/**
 *
 * @author antonio
 */
@Remote
public interface DirectionRemote {
    public List<DirectionDTO> getAllDirection();
}
