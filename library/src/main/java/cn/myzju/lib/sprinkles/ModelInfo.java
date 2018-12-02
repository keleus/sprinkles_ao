package cn.myzju.lib.sprinkles;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.myzju.lib.sprinkles.annotations.AutoIncrement;
import cn.myzju.lib.sprinkles.annotations.Column;
import cn.myzju.lib.sprinkles.annotations.DynamicColumn;
import cn.myzju.lib.sprinkles.annotations.Key;
import cn.myzju.lib.sprinkles.exceptions.AutoIncrementMustBeIntegerException;
import cn.myzju.lib.sprinkles.exceptions.DuplicateColumnException;
import cn.myzju.lib.sprinkles.exceptions.EmptyTableException;
import cn.myzju.lib.sprinkles.exceptions.NoKeysException;
import cn.myzju.lib.sprinkles.typeserializers.SqlType;

class ModelInfo {

    public static class ColumnField {
        String name;
        String sqlType;
        Field field;
        boolean isKey;
        boolean isAutoIncrement;
        boolean isDynamic;

        @Override
        public boolean equals(Object o) {
            if (o instanceof ColumnField) {
                return ((ColumnField) o).name.equals(name);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }
    }

    private static Map<Class<? extends QueryResult>, ModelInfo> cache = new HashMap<Class<? extends QueryResult>, ModelInfo>();

    String tableName;
    Set<ColumnField> columns = new HashSet<ColumnField>();
    List<ColumnField> keys = new ArrayList<ColumnField>();
    ColumnField autoIncrementField;

    private ModelInfo(){
        // hide contructor
    }

    static ModelInfo from(Class<? extends QueryResult> clazz) {
        if (cache.containsKey(clazz)) {
            return cache.get(clazz);
        }
        ModelInfo info = new ModelInfo();

        final Field[] fields = Utils.getAllDeclaredFields(clazz, Object.class);
        for (Field field : fields) {
            if (field.isAnnotationPresent(DynamicColumn.class)) {
                ColumnField column = new ColumnField();
                column.isDynamic = true;

                column.name = field.getAnnotation(DynamicColumn.class).value();
                column.sqlType = Sprinkles.sInstance.getTypeSerializer(field.getType()).getSqlType().name();
                column.field = field;

                if (!info.columns.add(column)) {
                    throw new DuplicateColumnException(column.name);
                }

            } else if (field.isAnnotationPresent(Column.class)) {
                ColumnField column = new ColumnField();
                column.isAutoIncrement = field.isAnnotationPresent(AutoIncrement.class);
                column.isKey = field.isAnnotationPresent(Key.class) || column.isAutoIncrement;

                column.name = field.getAnnotation(Column.class).value();
                column.sqlType = Sprinkles.sInstance.getTypeSerializer(field.getType()).getSqlType().name();
                column.field = field;

                if (column.isAutoIncrement && !column.sqlType.equals(SqlType.INTEGER.name())) {
                    throw new AutoIncrementMustBeIntegerException(column.name);
                }
                if (column.isAutoIncrement) {
                    info.autoIncrementField = column;
                }
                if (column.isKey) {
                    info.keys.add(column);
                }

                if (!info.columns.add(column)) {
                    throw new DuplicateColumnException(column.name);
                }
            }
        }

        if (info.columns.isEmpty()) {
            throw new EmptyTableException(clazz.getName());
        }
        if (Model.class.isAssignableFrom(clazz)) {
            info.tableName = Utils.getTableName((Class<? extends Model>) clazz);
            if (info.keys.size() == 0) {
                throw new NoKeysException();
            }
        }

        cache.put(clazz, info);
        return info;
    }

}
