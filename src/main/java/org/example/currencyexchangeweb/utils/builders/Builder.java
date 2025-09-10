package org.example.currencyexchangeweb.utils.builders;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Builder <E>{
    public E build(ResultSet rs) throws SQLException;
}
