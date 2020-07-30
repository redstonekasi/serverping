# Server Ping
Server Ping is a server-side mod that allows spoofing the player list and count displayed on the server screen.
## Installation
1. Download and install [Fabric Loader](https://fabricmc.net/use/) if you haven't already.
2. Get the latest release of Server Ping from the [releases]() tab.
3. Put it in your mod folder.
## Configuration
The config is located at `config/serverping.json5`

```json5
{
	"player_list": {
		"enabled": false, // Enables spoofing the player list
		"value": [ // Names to display, make this empty to hide player list
			"Fake Player",
			"Some other dude"
		]
	},
	"player_count": {
		"enabled": false, // Enables spoofing the player count
		"value": {
			"count": 1, // Current players
			"max": 12 // Max players
		}
	}
}
```