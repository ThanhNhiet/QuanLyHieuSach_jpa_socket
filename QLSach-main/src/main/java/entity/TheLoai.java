package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;
@Entity @Table(name = "theloai")
public class TheLoai implements Serializable{
	private static final long serialVersionUID = 5233515208980637667L;
	@Id
	private String maTheLoai;
	@Column(columnDefinition = "nvarchar(255)")
	private String tenTheLoai;
	public TheLoai(String maTheLoai, String tenTheLoai) {
		super();
		this.maTheLoai = maTheLoai;
		this.tenTheLoai = tenTheLoai;
	}
	public TheLoai() {
		super();
	}
	public TheLoai(String maTheLoai) {
		super();
		this.maTheLoai=maTheLoai;
	}
	public String getMaTheLoai() {
		return maTheLoai;
	}
	public void setMaTheLoai(String maTheLoai) {
		this.maTheLoai = maTheLoai;
	}
	public String getTenTheLoai() {
		return tenTheLoai;
	}
	public void setTenTheLoai(String tenTheLoai) {
		this.tenTheLoai = tenTheLoai;
	}
	@Override
	public String toString() {
		return "TheLoai [maTheLoai=" + maTheLoai + ", tenTheLoai=" + tenTheLoai + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(maTheLoai, tenTheLoai);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TheLoai other = (TheLoai) obj;
		return Objects.equals(maTheLoai, other.maTheLoai) && Objects.equals(tenTheLoai, other.tenTheLoai);
	}
	

}
