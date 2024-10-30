package com.exchange.achat.service;

import com.exchange.achat.dto.AchatDTO;
import com.exchange.achat.dto.AchatReq;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AchatService {
    public AchatDTO addAchat(AchatReq achatReq);
    public AchatDTO updateAchat(Long id, AchatDTO achatDTO);
    public AchatDTO getAchatbyId(Long id);
    public List<AchatDTO> getAllAchat();
    public void deleteAchat(Long id);

}
