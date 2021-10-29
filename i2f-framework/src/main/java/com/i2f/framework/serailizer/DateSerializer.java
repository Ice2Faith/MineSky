package com.i2f.framework.serailizer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.i2f.common.data.Constants;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ltb
 * @date 2021/9/2
 */
@JsonComponent
public class DateSerializer<T extends Date> extends JsonSerializer<T> {
    private SimpleDateFormat fmt=new SimpleDateFormat(Constants.PROJ_DEFAULT_DATE_FMT);
    @Override
    public void serialize(T date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(fmt.format(date));
    }
}
