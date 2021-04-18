/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.com.drSnehalAyuCareClinic.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.core.style.ToStringCreator;

/**
 *
 * @author Sonal Wankhede
 */
@Entity
@Table(name = "drugs")
public class Drug extends BaseEntity {
	@Column(name = "brand_Name")
	private String brandName;

	@Column(name = "content")
	private String content;

	@Column(name = "strength")
	private String strength;

	@Column(name = "form_Of_Drugs")
	private String formOfDrugs;
	
	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getFormOfDrugs() {
		return formOfDrugs;
	}

	public void setFormOfDrugs(String formOfDrugs) {
		this.formOfDrugs = formOfDrugs;
	}

	@Override
    public String toString() {
        return new ToStringCreator(this)
            .append("id", this.getId())
            .append("new", this.isNew())
            .append("brandName", this.brandName)
            .append("content", this.content)
            .append("strength",this.strength)
            .append("formOfDrugs", this.formOfDrugs)
            .toString();
    }
}
