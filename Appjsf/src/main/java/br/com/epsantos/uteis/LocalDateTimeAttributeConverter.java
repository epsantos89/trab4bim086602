package br.com.epsantos.uteis;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/*Classe é um conversor para corrigirmos o problema de cadastro de data com LocalDateTime no JPA*/

@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {


    //TRANSFORMA EM Timestamp NA HORA DE PERSISTIR NO BANCO DE DADOS
    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {

    	if(localDateTime != null)
    		return Timestamp.valueOf(localDateTime);

    	return null;

    }

    //TRANSFORMA UM Timestamp EM LocalDateTime QUANDO RETORNAR DO BANCO PARA ENTIDADE
    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {

    	if(timestamp != null)
    		return timestamp.toLocalDateTime();

    	return null;
    }
}