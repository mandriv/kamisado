package kamisado_mp;

import java.util.Date;

public class User {
	
	/*var userSchema = mongoose.Schema({
    name: {
        type: String,
        required: true,
        index: {
            unique: true
        }
    },
    email: {
        type: String,
        required: true,
        index: {
            unique: true
        }
    },
    password: {
        type: String,
        required: true
    },
    dateJoined: {
        type: Date,
        default: Date.now
    },
    gamesPlayerd: {
        type: Number,
        default: 0
    },
    gamesWon: {
        type: Number,
        default: 0
    },
    admin: {
        type: Boolean,
        default: false
    }
})
*/
	
	public String name;
	public String email;
	public Date dateJoined;
	public String hashedPassword;
	public int gamesPlayed;
	public int gamesWon;
	public int elo;
	public boolean admin;
	
	public User(String name, String email, Date dateJoined, String hashedPassword, int gamesPlayed, int gamesWon, int elo, boolean admin) {
		this.name = name;
		this.email = email;
		this.dateJoined = dateJoined;
		this.hashedPassword = hashedPassword;
		this.gamesPlayed = gamesPlayed;
		this.gamesWon = gamesWon;
		this.elo = elo;
		this.admin = admin;
	}
	

}
