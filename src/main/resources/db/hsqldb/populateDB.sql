INSERT INTO users(username,password,enabled) VALUES ('admin','{noop}admin', true);

INSERT INTO roles (username, role) VALUES ('admin', 'ROLE_ADMIN');

INSERT INTO issues (id, issues) VALUES (1, 'High BP');
INSERT INTO issues (id, issues) VALUES (2, 'Low BP');

INSERT INTO allergies (id, allergy) VALUES (1, 'Peanuts');
INSERT INTO allergies (id, allergy) VALUES (2, 'Sulpha');
INSERT INTO allergies (id, allergy) VALUES (3, 'Bananas');

INSERT INTO patients (id, first_name, last_name,
  birth_date, gender, address,
  city, telephone, history, allergies ) VALUES (1, 'Sonal', 'Wankhede', '05/01/1993', 'F', 'Snehswapn', 'Khamgaon', '9657730638', null, null);
  
INSERT INTO patients (id, first_name, last_name,
  birth_date, gender, address,
  city, telephone, history, allergies ) VALUES (2, 'Swapnil', 'Wankhede', '08/20/1989', 'F', 'Hinjewadi', 'Pune', '766679991', null, null);
  
 INSERT INTO drugs (id, name, brand_name, form_of_drugs, strength, content) VALUES (1, 'Paracetamol', 'Dolo', 'Tablet', '65mg', 'paracetamol');
 
 INSERT INTO diagnosis ( id, diagnosis) VALUES (1, 'Fungal Infection');
 
 INSERT INTO diagnosis ( id, diagnosis)  VALUES (2, 'Viral Fever');
  
