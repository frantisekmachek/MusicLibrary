# :musical_note: MusicLibrary
MusicLibrary is a Java music player where you can manage your songs and albums.

*Icons used in this program were made by [**Font Awesome**](https://fontawesome.com/search?m=free&o=r).*

## :cd: Songs
### :heavy_plus_sign: Import songs
To import a song to the library, you have to navigate to the **"Songs"** tab. There you'll find the **"Import New"** button.

When you click on the button, a **file explorer** opens where you have to choose a .wav file[^1]. Upon choosing one, you can choose the song **title, the artist's name and the album
the song belongs to**. You don't have to choose an album, and you can change it later on if you wish.

> [!NOTE]
> When you import a song, the sound file is moved to the program's resource directory.
> That also applies to album covers.

### :play_or_pause_button: Play songs
A music player wouldn't make much sense if it couldn't **play music**. Once you've imported a song, you can **click on it to play it**. You can then **pause and resume** as you wish.

There is also a **playback slider** where you can skip to different parts of the song. You can **go back to the previous song** or **skip to the next song**
by clicking on the backward or forward buttons respectively.

### :clock230: Song queue
You can add songs to the queue. The next song in the queue will start playing when the current one finishes.

## :musical_score: Create albums
To create an album, you have to navigate to the **"Albums"** tab. Click on the **"Create Album"** button. You'll be prompted to choose the album title and the artist's name.
When you're done, you click on "Save".

## :pencil2: Edit songs and albums
You can edit songs and albums by right-clicking on them and choosing the **"Edit"** option. You'll then see the same dialogs you see when you're importing.

Albums also offer the **"Change cover"** option, which opens a file explorer and prompts you to **choose an image file**. The image you choose will be the **album's cover**
and **all the songs belonging to that album will also display it**.

## :wastebasket: Remove songs and albums
When you're tired of a song or album, you can remove it by right-clicking on it and choosing the **"Remove"** option.

[^1]: Java doesn't natively support audio formats such as mp3. This program doesn't use any external libraries.
