package com.exchange.achat.service;


import com.exchange.achat.dto.AchatDTO;
import com.exchange.achat.dto.AchatReq;
import com.exchange.achat.dto.ProductDTO;
import com.exchange.achat.mapper.AchatMapper;
import com.exchange.achat.module.Achat;
import com.exchange.achat.repository.AchatRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AchatServiceImp  implements AchatService{
    @Autowired
    private AchatRepository achatRepository;
    private AchatMapper achatMapper;
    private final WebClient webClient;

    @Autowired
    public AchatServiceImp(AchatRepository achatRepository, AchatMapper achatMapper, WebClient webClient) {
        this.achatRepository = achatRepository;
        this.achatMapper = achatMapper;
        this.webClient = webClient;
    }


    @Override
    public AchatDTO addAchat(AchatReq achatReq) {
        Achat achat = achatMapper.reqToEntity(achatReq);
        achat.setDate(new Date());

        List<ProductDTO> productDTOList = convertProductsPrices(
                achatReq.getCurrency(),
                fetchProductDTOs(
                        achat.getProductsIds()
                )
        );

        Double total = calculateTotal(productDTOList);

        achat.setTotal(total);

        achatRepository.save(achat);

        return achatMapper.toDto(achat, productDTOList);
    }

    private Double fetchExchangeRate(String currency) {
        try {

            JsonNode response = webClient.get()
                    .uri("https://v6.exchangerate-api.com/v6/42624c0194dbdefe1615b54e/latest/EUR")
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();

            //log.info("Full API response: {}", response);

            if (response == null || !response.has("conversion_rates")) {
                throw new RuntimeException("Failed to fetch exchange rates. Response was null or incomplete.");
            }

            JsonNode conversionRates = response.get("conversion_rates");
            if (conversionRates == null || !conversionRates.has(currency)) {
                throw new RuntimeException("Exchange rate for currency " + currency + " not found.");
            }

            Double exchangeRate = conversionRates.get(currency).asDouble();
            return exchangeRate;

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch exchange rates.", e);
        }
    }

    private List<ProductDTO> convertProductsPrices ( String currency, List<ProductDTO> productDTOList){
        Double exchangeRate = fetchExchangeRate(currency);
        for (ProductDTO productDTO: productDTOList
        ) {
            productDTO.setPrice(
                    productDTO.getPrice() * exchangeRate
            );
        }
        return productDTOList;
    }

    private Double calculateTotal(List<ProductDTO> productsList) {
        return productsList.stream()
                .map(ProductDTO::getPrice)
                .reduce(0.0, Double::sum);

    }
    private List<ProductDTO> fetchProductDTOs(List<Long> productIds) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        ProductDTO productDTO = new ProductDTO();
        for (Long productId: productIds
        ) {
            productDTO = webClient.get()
                    .uri("http://localhost:8080/api/product/"+productId)
                    .retrieve()
                    .bodyToMono(ProductDTO.class)
                    .block();
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    @Override
    public AchatDTO updateAchat(Long id, AchatDTO achatDTO) {
        Optional<Achat> achatOptional = achatRepository.findById(id);
        if(achatOptional.isPresent()){
            Achat achat = achatOptional.get();
            achat.setDate(achatDTO.getDate());
            achat.setCurrency(achatDTO.getCurrency());
            achat.setTotal(achatDTO.getTotal());

            List<Long> productIds = achatDTO.getProducts().stream()
                    .map(ProductDTO::getId)
            .collect(Collectors.toList());
            achat.setProductsIds(productIds);

            Achat updateAchat = achatRepository.save(achat);

            List<ProductDTO> productDTOS=fetchProductDTOs(productIds);
            return achatMapper.toDto(updateAchat, productDTOS);
        }else {
            throw new RuntimeException("Achat not found with id "+id);
        }


    }

    @Override
    public AchatDTO getAchatbyId(Long id) {
        Achat achat = achatRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Achat not found with id " + id));
        List<ProductDTO> productDTOList = fetchProductDTOs(achat.getProductsIds());
        return achatMapper.toDto(achat, productDTOList);
    }

    @Override
    public List<AchatDTO> getAllAchat() {
        List<Achat> achats = achatRepository.findAll();
        List<AchatDTO> achatDTOList = new ArrayList<>();
        List<ProductDTO> productDTOList = new ArrayList<>();
        AchatDTO achatDTO = new AchatDTO();
        for (Achat achat: achats
        ) {
            productDTOList = fetchProductDTOs(achat.getProductsIds());
            achatDTO = achatMapper.toDto(achat, productDTOList);
            achatDTOList.add(achatDTO);
        }
        return achatDTOList;
    }

    @Override
    public void deleteAchat(Long id) {
        Achat achat = achatRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Achat not found with id "+ id));
        achatRepository.delete(achat);
    }
}
