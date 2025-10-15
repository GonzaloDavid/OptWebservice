package rpg.core;

import java.math.BigDecimal;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * The MessageMap class is the abstract superclass of all of the
 * message objects created by the AS/400 CRTCLASS command.
 *
 * @tag Copyright (C) 2013 Datapro Inc. All Rights Reserved.
 *
 * @tag Start date: (02/14/2013 5:57:39 PM)
 *
 * @author fhernandez
 *
 * @tag    Date          Flag        Description
 */
public abstract class MessageMap implements Map
{
    final static String COPYRIGHT =  "Copyright (C) 2013 Datapro Inc. All Rights Reserved";

    static class Entry implements Map.Entry {
        String name;
        String tag;
        Object value;
        protected Entry(String name, String tag, Object value) {
            this.name = name;
            this.tag = tag;
            this.value = value;
        }
        public Object getKey() {
            return tag;
        }
        public String getName() {
            return name;
        }
        public String getTag() {
            return tag;
        }
        public Object getValue() {
            return value;
        }
        public Object setValue(Object value) {
            Object oldValue = this.value;
            this.value = value;
            return oldValue;
        }
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry e = (Map.Entry) o;
            return (getKey() == null ? e.getKey() == null : getKey().equals(e.getKey()))
                    && (getValue() == null ? e.getValue() == null : getValue().equals(e.getValue()));
        }
        public int hashCode() {
            return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
        }
        public String toString() {
            return getKey() + "=" + getValue();
        }
    }

    public void clear() {
        throw new UnsupportedOperationException("Can't remove Fields, this is a fixed Object");
    }

    public Object remove(Object key) {
        throw new UnsupportedOperationException("Can't remove Fields, this is a fixed Object");
    }

    protected class MessageFieldCollection extends AbstractCollection {
        MessageRecord mr;
        protected MessageFieldCollection(MessageRecord mr) {
            this.mr = mr;
        }
        public int size() {
            return MessageMap.this.size();
        }
        public Iterator iterator() {
            return new MessageEnumerator(mr);
        }
    }

    protected class MessageEntryEnumerator extends MessageEnumerator {
        protected MessageEntryEnumerator(MessageRecord mr) {
            super(mr);
        }
        public Object nextElement() throws NoSuchElementException {
            MessageField field = (MessageField)super.nextElement();
            return new MessageMap.Entry(
                    myrec.getFieldName(field.getTag()),
                    field.getTag(),
                    field);
        }
    }

    protected class MessageFieldEntrySet extends AbstractSet {
        MessageRecord mr;
        protected MessageFieldEntrySet(MessageRecord mr) {
            this.mr = mr;
        }
        public boolean contains(Object object) {
            return MessageMap.this.containsKey(object);
        }
        public int size() {
            return MessageMap.this.size();
        }
        public Iterator iterator() {
            return new MessageEntryEnumerator(mr);
        }
    }

    protected class MessageFieldKeySet extends MessageFieldEntrySet {
        protected MessageFieldKeySet(MessageRecord mr) {
            super(mr);
        }
        public Iterator iterator() {
            return new MessageEntryEnumerator(mr){
                private boolean onTag;
                public Object nextElement() throws NoSuchElementException {
                    MessageMap.Entry entry = null;
                    onTag = !onTag;
                    if (onTag) {
                        entry = (MessageMap.Entry)super.nextElement();
                        return entry.getTag();
                    } else {
                        return entry.getName();
                    }
                }
            };
        }
    }

    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    public Object put(Object key, Object value) {
        if (key instanceof String) {
            Object oldValue = null;
            if (containsKey(key)) {
                MessageField field = (MessageField) get(key);
                if (field instanceof DecimalField) {
                    DecimalField dField = (DecimalField)field;
                    oldValue = dField.getBigDecimal();
                    if (value instanceof BigDecimal) {
                        dField.setBigDecimal((BigDecimal) value);
                    } else if (value instanceof Number) {
                        dField.setBigDecimal(new BigDecimal(""+((Number)value).doubleValue()));
                    } else {
                        dField.setString(value.toString());
                    }
                } else {
                    oldValue = field.getString();
                    field.setString(value.toString());
                }
            }
            return oldValue;
        }
        throw new ClassCastException("This Message Map only supports String keys");
    }

    public void putAll(Map map) {
        Iterator i = map.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry e = (Map.Entry) i.next();
            put(e.getKey(), e.getValue());
        }
    }

    public Collection values() {
        return new MessageFieldCollection((MessageRecord) this);
    }

    public Set entrySet() {
        return new MessageFieldEntrySet((MessageRecord) this);
    }

    public Set keySet() {
        return new MessageFieldKeySet((MessageRecord) this);
    }
}
