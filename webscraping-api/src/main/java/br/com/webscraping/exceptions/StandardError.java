package br.com.webscraping.exceptions;

import lombok.Data;
import java.io.Serializable;

@Data
public class StandardError implements Serializable {
        private String timestamp;
        private Integer status;
        private String error;
        private String message;
        private String path;
}
