package DegreeEZ;

import java.util.UUID;

public  abstract class User {
        protected final UUID uuid;
        private String firstName;
        private String lastName;
        private String password;
        private String userName;
        
        public User(UUID uuid, String userName, String firstName, String lastName, String password) {
            this.uuid = uuid;
            this.firstName = firstName;
            this.lastName = lastName;
            this.password = password;
            this.userName = userName;
        }
    
        public UUID getUUID() {
            return uuid;
        }

        public String getFirstName() {
            return firstName;
        }
    
        public String getLastName() {
            return lastName;
        }
    
        public String getPassword() {
            return password;
        } 

        public String getUserName() {
            return userName;
        }

        public boolean isAdvisor() {
            return this instanceof Advisor;
        }
    }

