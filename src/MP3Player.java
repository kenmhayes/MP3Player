
public class MP3Player {

    private MP3Player() {

    }

    public static void main(String[] args) {
        MP3PlayerModel model = new MP3PlayerModel();
        MP3PlayerView view = new MP3PlayerView();
        MP3PlayerController controller = new MP3PlayerController(model, view);

        System.out.print(model.mediaPlayer().getMedia().getSource());

        view.registerObserver(controller);
    }

}
