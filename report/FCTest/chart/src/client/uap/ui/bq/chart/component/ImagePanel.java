package uap.ui.bq.chart.component;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

class ImagePanel extends JLabel {

		private static final long serialVersionUID = 1L;

		public Image image = null;
		public int imgWidth = -1;
		public int imgHeight = -1;

		public ImagePanel() {
		}

		public Image getImage() {
			return image;
		}

		public void setImage(Image image) {
			this.image = image;
			this.imgWidth = image.getWidth(this);
			this.imgHeight = image.getHeight(this);

			// IconWrap icon = new IconWrap();
			// icon.setIcon(new ImageIcon(image));
			this.setIcon(new ImageIcon(image));
		}
	}