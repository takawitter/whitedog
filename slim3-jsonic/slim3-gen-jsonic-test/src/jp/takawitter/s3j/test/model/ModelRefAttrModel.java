package jp.takawitter.s3j.test.model;

import jp.takawitter.s3j.test.meta.ModelRefAttrModelMeta;

import org.slim3.datastore.Attribute;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.Model;
import org.slim3.datastore.ModelRef;

import com.google.appengine.api.datastore.Key;

@Model
public class ModelRefAttrModel {
	public ModelRefAttrModel(){
	}
	public ModelRefAttrModel(String name){
		this.key = Datastore.createKey(ModelRefAttrModelMeta.get(), name);
		this.name = name;
	}
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ModelRef<ModelRefAttrModel> getRef() {
		return ref;
	}

	@Attribute(primaryKey=true)
	private Key key;
	private String name;
	private ModelRef<ModelRefAttrModel> ref = new ModelRef<ModelRefAttrModel>(ModelRefAttrModel.class);
}
