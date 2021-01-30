package com.stefan.DailyTest;

import fai.comm.util.*;

import java.nio.ByteBuffer;
import java.util.*;

@SuppressWarnings("serial")
public class FaiList<T> extends ArrayList<T> {
	public FaiList() {
	}
	
	public FaiList(Collection<? extends T> c) {
	    super(c);
	}
	
	public FaiList(int size) {
		super(size);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public FaiList<T> clone() {
		FaiList<T> list = new FaiList<T>();
		Iterator<T> iter = this.iterator();
		while (iter.hasNext()) {
			T obj = iter.next();
			list.add((T) Var.clone(obj));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
	    if (!(obj instanceof FaiList)) {
	        return false;
	    }
	    
		FaiList<T> list = (FaiList<T>) obj;
		if (list.size() != this.size()) {
			return false;
		}
		for (int i = 0; i < list.size(); i++) {
			if (!this.get(i).equals(list.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	// 1 2 3 -> [1,2,3]
	public String toJson() {
		StringBuilder buf = new StringBuilder(100);
		toJson(buf);
		return buf.toString();
	}

	// 1 2 3 -> [1,2,3]
	public void toJson(StringBuilder buf) {
		buf.append("[");
		Iterator<T> iter = iterator();
		boolean hasNext = iter.hasNext();
		while (hasNext) {
			T value = iter.next();
			Var.toJson(value, buf);
			hasNext = iter.hasNext();
			if (hasNext) {
				buf.append(",");
			}
		}
		buf.append("]");
	}

	// 1 2 3 -> [1,2,3]
	public String toHtmlJson() {
		StringBuilder buf = new StringBuilder(100);
		toHtmlJson(buf);
		return buf.toString();
	}

	// 1 2 3 -> [1,2,3]
	public void toHtmlJson(StringBuilder buf) {
		buf.append("[");
		Iterator<T> iter = iterator();
		boolean hasNext = iter.hasNext();
		while (hasNext) {
			T value = iter.next();
			Var.toHtmlJson(value, buf);
			hasNext = iter.hasNext();
			if (hasNext) {
				buf.append(",");
			}
		}
		buf.append("]");
	}

	/* 不应该有这种场景
	public String toJsJson() {
		StringBuilder buf = new StringBuilder(100);
		toJsJson(buf);
		return buf.toString();
	}

	public void toJsJson(StringBuilder buf) {
		buf.append("[");
		Iterator<T> iter = iterator();
		boolean hasNext = iter.hasNext();
		while (hasNext) {
			T value = iter.next();
			Var.toJsJson(value, buf);
			hasNext = iter.hasNext();
			if (hasNext) {
				buf.append(",");
			}
		}
		buf.append("]");
	}
	*/
	
	public String toJsonLine() {
		StringBuilder buf = new StringBuilder(100);
		toJsonLine(buf);
		return buf.toString();
	}

	public void toJsonLine(StringBuilder buf) {
		toJsonLine( buf, 0 );
	}
	
	public void toJsonLine(StringBuilder buf, int tabs) {
		String tabs1 = "";
		for (int i = 0; i < tabs; i++) {
			tabs1 += "  ";
		}
		String tabs2 = tabs1 + "  ";
		buf.append("[\n" + tabs2);
		Iterator<T> iter = iterator();
		boolean hasNext = iter.hasNext();
		while (hasNext) {
			T value = iter.next();
			Var.toJsonLine(value, buf, tabs+1);
			hasNext = iter.hasNext();
			if (hasNext) {
				buf.append(",\n" + tabs2);
			}
		}
		buf.append("\n" + tabs1 + "]");
	}

	// 1 2 3 -> [[1],[2],[3]]
	public String toHtmlJsonArray() {
		StringBuilder buf = new StringBuilder(100);
		toHtmlJsonArray(buf);
		return buf.toString();
	}

	// 1 2 3 -> [[1],[2],[3]]
	public void toHtmlJsonArray(StringBuilder buf) {
		buf.append("[");
		Iterator<T> iter = iterator();
		boolean hasNext = iter.hasNext();
		while (hasNext) {
			T value = iter.next();
			buf.append("[");
			Var.toHtmlJson(value, buf);
			buf.append("]");
			hasNext = iter.hasNext();
			if (hasNext) {
				buf.append(",");
			}
		}
		buf.append("]");
	}

	// 1 2 3 -> [[1],[2],[3]]
	public String toJsonArray() {
		StringBuilder buf = new StringBuilder(100);
		toJsonArray(buf);
		return buf.toString();
	}

	// 1 2 3 -> [[1],[2],[3]]
	public void toJsonArray(StringBuilder buf) {
		buf.append("[");
		Iterator<T> iter = iterator();
		boolean hasNext = iter.hasNext();
		while (hasNext) {
			T value = iter.next();
			buf.append("[");
			Var.toJson(value, buf);
			buf.append("]");
			hasNext = iter.hasNext();
			if (hasNext) {
				buf.append(",");
			}
		}
		buf.append("]");
	}

	// param list to value array -> [['a1','b1'],['a2','b2']]
	public String toJsonArray(FaiList<String> keyList) {
		StringBuilder buf = new StringBuilder(512);
		toJsonArray(keyList, buf);
		return buf.toString();
	}

	// param list to value array -> [['a1','b1'],['a2','b2']]
	public void toJsonArray(FaiList<String> keyList, StringBuilder buf) {
		buf.append('[');
		boolean first1 = true;
		for (int i = 0; i < size(); i++) {
		    Object obj = get(i);
			Param info = (Param) obj;
			if (info == null) {
				continue;
			}
			if (first1) {
				first1 = false;
			} else {
				buf.append(',');
			}
			buf.append('[');

			boolean first2 = true;
			for (int j = 0; j < keyList.size(); j++) {
			    String key = keyList.get(j);
				Object value = info.getObject(key);
				if (value == null) {
					continue;
				}
				if (first2) {
					first2 = false;
				} else {
					buf.append(',');
				}
				Var.toJson(value, buf);
			}

			buf.append(']');
		}
		buf.append(']');
	}
	
	@Override
    public String toString() {
        return toJson();
    }

	public static FaiList<Integer> parseIntList(String json) {
		return parseList(json, Var.Type.INT);
	}
	
	public static FaiList<Integer> parseIntList(String json, FaiList<Integer> defaultList) {
		FaiList<Integer> result = parseList(json, Var.Type.INT);
		return result == null ? defaultList : result;
	}
	
	public static FaiList<Long> parseLongList(String json) {
		return parseList(json, Var.Type.LONG);
	}

	public static FaiList<Long> parseLongList(String json, FaiList<Long> defaultList) {
		FaiList<Long> result = parseList(json, Var.Type.LONG);
		return result == null ? defaultList : result;
	}
	
	public static FaiList<String> parseStringList(String json) {
		return parseList(json, Var.Type.STRING);
	}
	
	public static FaiList<String> parseStringList(String json, FaiList<String> defaultList) {
		FaiList<String> result = parseList(json, Var.Type.STRING);
		return result == null ? defaultList : result;
	}

	public static FaiList<Param> aramList(String json) {
		return parseList(json, Var.Type.PARAM);
	}
	
	public static FaiList<Param> parseParamList(String json, FaiList<Param> defaultList) {
		FaiList<Param> result = parseList(json, Var.Type.PARAM);
		return result == null ? defaultList : result;
	}
	
	public static FaiList<Param> parseParamList(String json) {
		FaiList<Param> result = parseList(json, Var.Type.PARAM);
		return result == null ? null : result;
	}

	public static <T> FaiList<T> parseList(String json, byte type) {
		if (json == null || json.isEmpty()) {
			return null;
		}
		int len = json.length();
		char c;
		int i = 0;
		for (i = 0; i < len; ++i) {
			c = json.charAt(i);
			if (c == ' ' || c == '\n' || c == '\r' || c == '\t') {
				continue;
			}
			if (c == '[') {
				break;
			}
			Log.logErr("not begin with '[';json=%s", json);
			return null;
		}
		return parseList(json, i, null, type);
	}

	@SuppressWarnings("unchecked")
	public static <T> FaiList<T> parseList(String json, int start, Ref<Integer> endRef, byte type) {
		int i = start;
		try {
			int len = json.length();
			FaiList<T> list = new FaiList<T>();
			boolean valueStarted = true; // 是否可以开始一个value
			char c = json.charAt(i);
			if (c != '[') {
				Log.logErr("not start with '[';i=%d;json=%s", i, Str.cut(json, 1000));
				return null;
			}
			++i;
			for (; i < len; ++i) {
				c = json.charAt(i);

				if (c == ' ' || c == '\n' || c == '\r' || c == '\t') {
					continue;
				}

				if (c == ']') {
					if (list.isEmpty()) {
						// []的情况
						valueStarted = false;						
					}
					break;
				}

				if (valueStarted) {
					Ref<Integer> valueEndRef = new Ref<Integer>();
					Object value = Var.parseJsValue(json, i, valueEndRef);
					if (value == null) {
						return null;
					}
					if (!(value instanceof Null)) {
						switch (type)
						{
						case Var.Type.INT: 
							if (!(value instanceof Integer))
							{
								Log.logErr("value is not int");
								return null;
							}
							break;
						case Var.Type.LONG: 
							if ( !(value instanceof Long) && !(value instanceof Integer) )
							{
								Log.logErr("value is not long or integer");
								return null;
							}
							if( value instanceof Integer ){
								Integer value_i = (Integer) value;
								value = (Long)value_i.longValue();
							}
							break;
                        case Var.Type.DOUBLE:
                            if (!(value instanceof Double))
                            {
                                Log.logErr("value is not Double");
                                return null;
                            }
                            break;
						case Var.Type.STRING: 
							if (!(value instanceof String))
							{
								Log.logErr("value is not string");
								return null;
							}
							break;
						case Var.Type.PARAM: 
							if (!(value instanceof Param))
							{
								Log.logErr("value is not param");
								return null;
							}
							break;
						case Var.Type.FAI_LIST: 
							if (!(value instanceof FaiList))
							{
								Log.logErr("value is not FaiList");
								return null;
							}
							break;
						case Var.Type.OBJECT:
							break;
						default:
							Log.logErr("unknown type " + type);
							return null;
						}
						list.add((T) value);
					}
					i = valueEndRef.value;
					valueStarted = false;
					continue;
				}

				if (c == ',') {
					valueStarted = true;
					continue;
				}

				Log.logErr("value not started;i=%d;json=%s", i, Str.cut(json, 1000));
				return null;
			}

			// 循环结束
			if (valueStarted) {
				Log.logErr("ending err;json=%s", Str.cut(json, 1000));
				return null;
			}
			return list;
		} catch (Exception exp) {
			Log.logErr(exp, "json:" + Str.cut(json, 1000));
			return null;
		} finally {
			if (endRef != null) {
				endRef.value = i;
			}
		}
	}

	public int toBuffer(FaiBuffer buf, int key) {
		return toBuffer(buf, key, null);
	}

	public int toBuffer(FaiBuffer buf, int key, ParamDef paramDef) {
		int rt = Errno.ERROR;
		if (buf == null) {
			rt = Errno.ARGS_ERROR;
			Log.logErr(rt, "buf=null");
			return rt;
		}
		rt = buf.encodeDef(key, FaiBuffer.Type.GROUP_BEG);
		if (rt != Errno.OK) {
			return rt;
		}

		for (int i = 0; i < size(); i++) {
		    Object obj = get(i);
			rt = Var.toBuffer(buf, key, obj, paramDef);
			if (rt != Errno.OK) {
				Log.logErr(rt, "codec err;obj=%s", obj);
				return rt;
			}
		}

		return buf.encodeDef(key, FaiBuffer.Type.GROUP_END);
	}

	public int fromBuffer(ByteBuffer buf, Ref<Integer> keyRef) {
		return fromBuffer(new FaiBuffer(buf), keyRef, null);
	}

	public int fromBuffer(FaiBuffer buf, Ref<Integer> keyRef) {
		return fromBuffer(buf, keyRef, null);
	}

	public int fromBuffer(ByteBuffer buf, Ref<Integer> keyRef, ParamDef paramDef) {
		return fromBuffer(new FaiBuffer(buf), keyRef, paramDef);
	}

	public int fromBuffer(FaiBuffer buf, Ref<Integer> keyRef, ParamDef paramDef) {
		int rt = Errno.ERROR;
		if (buf == null) {
			rt = Errno.ARGS_ERROR;
			Log.logErr(rt, "arg err");
			return rt;
		}

		Ref<Byte> typeRef = new Ref<Byte>();
		rt = buf.decodeDef(keyRef, typeRef);
		if (rt != Errno.OK) {
			return rt;
		}

		if (typeRef.value != FaiBuffer.Type.GROUP_BEG) {
			rt = Errno.CODEC_ERROR;
			Log.logErr(rt, "getParam no match;type=%d", typeRef.value);
			return rt;
		}

		return decodeList(buf, this, paramDef);
	}

	public static <T> FaiList<T> parseList(ByteBuffer buf, Ref<Integer> keyRef,
			ParamDef paramDef) {
		FaiList<T> list = new FaiList<T>();
		int rt = list.fromBuffer(buf, keyRef, paramDef);
		if (rt != Errno.OK) {
			return null;
		}
		return list;
	}

	public static <T> FaiList<T> parseList(FaiBuffer buf, Ref<Integer> keyRef,
			ParamDef paramDef) {
		FaiList<T> list = new FaiList<T>();
		int rt = list.fromBuffer(buf, keyRef, paramDef);
		if (rt != Errno.OK) {
			return null;
		}
		return list;
	}

	public static <T> FaiList<T> parseList(ByteBuffer buf, Ref<Integer> keyRef) {
		FaiList<T> list = new FaiList<T>();
		int rt = list.fromBuffer(buf, keyRef, null);
		if (rt != Errno.OK) {
			return null;
		}
		return list;
	}

	public static <T> FaiList<T> parseList(FaiBuffer buf, Ref<Integer> keyRef) {
		FaiList<T> list = new FaiList<T>();
		int rt = list.fromBuffer(buf, keyRef, null);
		if (rt != Errno.OK) {
			return null;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static <T> int decodeList(FaiBuffer buf, FaiList<T> list,
			ParamDef paramDef) {
		int rt = Errno.ERROR;
		if (buf == null || list == null) {
			rt = Errno.ARGS_ERROR;
			Log.logErr(rt, "arg err");
			return rt;
		}

		list.clear();
		Ref<Byte> typeRef = new Ref<Byte>();
		while (true) {
			// get list's object
			rt = buf.decodeDef(null, typeRef);
			if (rt != Errno.OK) {
				return rt;
			}
			if (typeRef.value == FaiBuffer.Type.GROUP_END) {
				return Errno.OK;
			}

			if (typeRef.value != FaiBuffer.Type.VARINT) {
				rt = Errno.CODEC_ERROR;
				Log.logErr(rt, "codec obj-type type err;type=%d",
								typeRef.value);
				return rt;
			}
			Ref<Object> objectRef = new Ref<Object>();
			rt = Var.decodeObject(buf, objectRef, paramDef);
			if (rt != Errno.OK) {
				return rt;
			}

			list.add((T) objectRef.value);
		}
	}
	
	public  boolean add(T o){
		if( m_readOnly ){
			throw new AssertionError();
		}
		return super.add(o);
	}
	
	public void add(int i, T o){
		if( m_readOnly ){
			throw new AssertionError();
		}
		super.add(i, o);
	}
	
	public  boolean addNotNull(T o){
		if(o == null){
			return false;
		}
		if( m_readOnly ){
			throw new AssertionError();
		}
		return super.add(o);
	}
	
	public void addNotNull(int i, T o){
		if(o == null){
			return;
		}
		if( m_readOnly ){
			throw new AssertionError();
		}
		super.add(i, o);
	}
	
	public boolean addAll(Collection<? extends T> c){
		if( m_readOnly ){
			throw new AssertionError();
		}
		return super.addAll(c);
	}
	
	public boolean addAll(int index, Collection<? extends T> c){
		if( m_readOnly ){
			throw new AssertionError();
		}
		return super.addAll(index, c);
	}
	
	public T remove(int i){
		if( m_readOnly ){
			throw new AssertionError();
		}
		return super.remove(i);
	}
	
	public boolean remove(Object o){
		if( m_readOnly ){
			throw new AssertionError();
		}
		return super.remove(o);
	}
	
	public boolean removeAll(Collection<?> c){
		if( m_readOnly ){
			throw new AssertionError();
		}
		return super.removeAll(c);
	}
	
	protected void removeRange(int fromIndex, int toIndex){
		if( m_readOnly ){
			throw new AssertionError();
		}
		super.removeRange(fromIndex, toIndex);
	}
	
	public T set(int i,T o){
		if( m_readOnly ){
			throw new AssertionError();
		}
		return super.set(i, o);
	};
	
	/*
	subList实现代码在AbstractList类里边，然而无论如何，最终的结果都是返回一个AbstractList的子类：SubList（该类是一个使
	用默认修饰符修饰的类，其源代码位于AbstractList.java类文件里边），
	SubList类的构造方法：
	SubList(AbstractList<E> list, int fromIndex, int toIndex) {   
	    if (fromIndex < 0)   
	        throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);   
	    if (toIndex > list.size())   
	        throw new IndexOutOfBoundsException("toIndex = " + toIndex);   
	    if (fromIndex > toIndex)   
	        throw new IllegalArgumentException("fromIndex(" + fromIndex +   
	                                           ") > toIndex(" + toIndex + ")");   
	    l = list;   
	    offset = fromIndex;   
	    size = toIndex - fromIndex;   
	    expectedModCount = l.modCount;   
	}
	里边，将我们原有的list对象给缓存到SubList类对象的一个属性中去了。而SubList类的add/remove等修改元素的方法中，都使用l进行了操作：
	因此，当我们使用子集合tempList进行元素的修改操作时，会影响原有的list集合。所以在使用subList方法时，一定要想清楚，是否需要对子集
	合进行修改元素而不影响原有的list集合。
	注:
	一般用subList不修改的方法是  ：
		 List<Object> tempList = new ArrayList<Object>(lists.subList(2, lists.size()));  
	*/
	public List<T> subList(int fromIndex, int toIndex){//
		if( m_readOnly ){
			Calendar cal = Calendar.getInstance();
			throw new AssertionError();
		}
		return super.subList(fromIndex, toIndex);
	}
	
	public void clear(){
		if( m_readOnly ){
			throw new AssertionError();
		}
		super.clear();
	}
	
	public void trimToSize(){
		if( m_readOnly ){
			throw new AssertionError();
		}
		super.trimToSize();
	}
	
	public String join(String str) {
		StringBuilder buf = new StringBuilder();
		Iterator<T> iter = iterator();
		boolean hasNext = iter.hasNext();
		while (hasNext) {
			T value = iter.next();
			buf.append(value.toString());
			hasNext = iter.hasNext();
			if (hasNext) {
				buf.append(str);
			}
		}
		return buf.toString();
	}
	
	public void setReadOnly(boolean readOnly) {
		setReadOnly(readOnly, null);
	}
	
	public void setReadOnly(boolean readOnly, boolean traversa) {
		if( traversa ){
			setReadOnly(readOnly, new HashSet<Object>());
		}else{
			setReadOnly(readOnly, null);
		}
	}
	
	/**
	 * @param readOnly 是否只读
	 * @param hashCodes 不为null时则迭代设置子元素中的Param和FaiList为 readOnly
	 */
	public void setReadOnly(boolean readOnly, Set<Object> hashCodes) {
		m_readOnly = readOnly;
		if( hashCodes != null && this.size() > 0 ){
			if( m_hashObj == null ){
				m_hashObj = new Object();
			}
			if( hashCodes.contains( m_hashObj ) ){
				//已经set过，就此返回，防止相互引用的死循环
				return;
			}
			hashCodes.add( m_hashObj );
			Object v = this.get(0);
			if( v instanceof FaiList<?> ){
				for( Object o : this ){
					((FaiList<?>)o).setReadOnly( readOnly, hashCodes );
				}
			}else if( v instanceof Param ){
				for( Object o : this ){
					((Param)o).setReadOnly(readOnly, hashCodes);
				}
			}
		}
	}
	
	
	public boolean getReadOnly() {
		return m_readOnly;
	}
	
	//避免 setReadOnly 死循环
	private Object m_hashObj = null;
	
	private boolean m_readOnly = false;
}