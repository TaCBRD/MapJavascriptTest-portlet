/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.omi.mapservice.portlet;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jschulte
 */
public class WKTLayer {
    private List<LayerFeature> Features;	
	private String id;
	private String name;
	private String url;
	private String description;
	private int zOrder=0;
	private String format;
    private String UserID;
    private String CommunityID;

    public WKTLayer() {
        Features = new ArrayList<LayerFeature>();
    }
    
    public List<LayerFeature> getFeatures() {
        return Features;
    }

    public void setFeatures(List<LayerFeature> Features) {
        this.Features = Features;
    }
     public boolean addFeature(LayerFeature feature){
         return Features.add(feature);
     }
	public String getId() {
		return id;
	}

	
	public void setId(String id) {
		this.id=id;
	}

	
	public String getName() {
		return this.name;
	}

	
	public void setName(String name) {
		this.name=name;
	}

	
	public String getUrl() {
		return this.url;
	}

	
	public void setUrl(String url) {
		this.url=url;
	}

	
	public String getDescription() {
		return this.description;
	}

	
	public void setDescription(String description) {
		this.description=description;
	}

	
	public int getZOrder() {
		return zOrder;
	}

	
	public void setZOrder(int zOrder) {
		this.zOrder=zOrder;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}
        
        public String getOwnerID() {
		return UserID;
	}
        
	public void setOwnerID(String UserID) {
		this.UserID = UserID;
	}
        
        public String getCommunityID() {
		return CommunityID;
	}
        
	public void setCommunityID(String CommunityID) {
		this.CommunityID = CommunityID;
	}
    
}
