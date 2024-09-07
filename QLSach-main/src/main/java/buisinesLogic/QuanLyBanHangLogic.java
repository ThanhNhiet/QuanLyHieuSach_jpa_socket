package buisinesLogic;

import dao.*;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Sach;
import entity.SanPham;
import entity.VPP;
import jakarta.persistence.EntityManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;

public class QuanLyBanHangLogic {
	private Socket socket;
	private EntityManager em;
	private NhanVienDAO nhanVienDAO;
	private KhachHangDAO khachHangDAO;
	private SanPhamDAO sanPhamDAO;
	private HoaDonDAO hoaDonDAO;
	private ChiTietHoaDonDAO chiTietHoaDonDAO;

	public QuanLyBanHangLogic(EntityManager em, Socket socket) {
		this.em = em;
		this.socket = socket;
	}

	public void handleClientRequest(Scanner scanner) {
		String action = scanner.nextLine();
		switch (action) {
		case "getBasic":
			getIDHDAutoGen();
			getNVByID(scanner);
			break;
		case "getAllSach":
			getAllSach();
			break;
		case "getAllVPP":
			getAllVPP();
			break;
		case "getSP":
			getSPByID(scanner);
			break;
		case "getKHBySDT":
			getKHBySDT(scanner);
			break;
		case "getAllSP":
			getAllSP();
			break;
		case "thanhToan":
			
			//Kiểm tra số lượng sách và VPP còn trong kho
			String stringRowJTable_DSSPBan_check = scanner.nextLine();
			int rowJTable_DSSPBan_check = Integer.parseInt(stringRowJTable_DSSPBan_check);
			for (int i = 0; i < rowJTable_DSSPBan_check; i++) {
				getSPByID(scanner);
			}
			
			getIDHDAutoGen();
			getNVByID(scanner);
			getKHBySDT(scanner);
			String hdObject = scanner.nextLine();
			HoaDon hd = new Gson().fromJson(hdObject, new TypeToken<HoaDon>() {
			}.getType());
			addHoaDon(hd);
			try {
				String stringRowJTable_DSSPBan = scanner.nextLine();
				int rowJTable_DSSPBan = Integer.parseInt(stringRowJTable_DSSPBan);
				for (int i = 0; i < rowJTable_DSSPBan; i++) {
					getSPByID(scanner);
					
					String idSP = scanner.nextLine();
					String cthdObject = scanner.nextLine();
					
					ChiTietHoaDon cthd = null;
					if (idSP.startsWith("S")) {
						InstanceCreator<SanPham> sachInstanceCreator = new InstanceCreator<SanPham>() {
							@Override
							public SanPham createInstance(Type type) {
								return new Sach();
							}
						};
						Gson gsonSach = new GsonBuilder().registerTypeAdapter(SanPham.class, sachInstanceCreator)
								.create();

						cthd = gsonSach.fromJson(cthdObject, new TypeToken<ChiTietHoaDon>() {
						}.getType());
					} else {
						InstanceCreator<SanPham> vppInstanceCreator = new InstanceCreator<SanPham>() {
							@Override
							public SanPham createInstance(Type type) {
								return new VPP();
							}
						};
						Gson gsonVPP = new GsonBuilder().registerTypeAdapter(SanPham.class, vppInstanceCreator)
								.create();
						cthd = gsonVPP.fromJson(cthdObject, new TypeToken<ChiTietHoaDon>() {
						}.getType());
					}

					addCTHD(cthd);
					
					String idSP2 = scanner.nextLine();
					SanPham sp = null;
					String spObject = scanner.nextLine();
					if (idSP.startsWith("S")) {
						InstanceCreator<SanPham> sachInstanceCreator = new InstanceCreator<SanPham>() {
							@Override
							public SanPham createInstance(Type type) {
								return new Sach();
							}
						};
						Gson gsonSach = new GsonBuilder().registerTypeAdapter(SanPham.class, sachInstanceCreator)
								.create();

						sp = gsonSach.fromJson(spObject, new TypeToken<SanPham>() {
						}.getType());
					} else {
						InstanceCreator<SanPham> vppInstanceCreator = new InstanceCreator<SanPham>() {
							@Override
							public SanPham createInstance(Type type) {
								return new VPP();
							}
						};
						Gson gsonVPP = new GsonBuilder().registerTypeAdapter(SanPham.class, vppInstanceCreator)
								.create();
						
						sp = gsonVPP.fromJson(spObject, new TypeToken<SanPham>() {
						}.getType());
					}
					
					updateSP(sp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			getAllCTHDByIDhd(scanner);

			getIDHDAutoGen();
			break;
		default:
			System.out.println("Unknown action: " + action);
		}

	}

	public void getIDHDAutoGen() {
		hoaDonDAO = new HoaDonDAO(em);
		String idAuto = hoaDonDAO.auto_IDPHoaDon();
		System.out.println(idAuto);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(idAuto);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void getNVByID(Scanner sc) {
		nhanVienDAO = new NhanVienDAO(em);
		String id = sc.nextLine();
		NhanVien nv = nhanVienDAO.getNhanVienByID(id);
		String response = new Gson().toJson(nv);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void getAllSach() {
		sanPhamDAO = new SanPhamDAO(em);
		List<SanPham> listSP = sanPhamDAO.getAllListSanPham();
		List<SanPham> listSach = new ArrayList<>();
		for (SanPham sp : listSP) {
			if (sp instanceof Sach) {
				listSach.add(sp);
			}
		}
		String response = new Gson().toJson(listSach);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void getAllVPP() {
		sanPhamDAO = new SanPhamDAO(em);
		List<SanPham> listSP = sanPhamDAO.getAllListSanPham();
		List<SanPham> listVPP = new ArrayList<>();
		for (SanPham sp : listSP) {
			if (sp instanceof VPP) {
				listVPP.add(sp);
			}
		}
		String response = new Gson().toJson(listVPP);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void getSPByID(Scanner sc) {
		sanPhamDAO = new SanPhamDAO(em);
		String id = sc.nextLine();
		SanPham sp = sanPhamDAO.getSanPhamById(id);
		String response = new Gson().toJson(sp);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public SanPham getSPByID_inclass(Scanner sc) {
		sanPhamDAO = new SanPhamDAO(em);
		String id = sc.nextLine();
		SanPham sp = sanPhamDAO.getSanPhamById(id);
		return sp;
	}

	public void getKHBySDT(Scanner sc) {
		khachHangDAO = new KhachHangDAO(em);
		String sdt = sc.nextLine();
		KhachHang kh = khachHangDAO.getKhachHangBySdt(sdt);
		String response = new Gson().toJson(kh);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void getAllSP() {
		sanPhamDAO = new SanPhamDAO(em);
		List<SanPham> listSP = sanPhamDAO.getAllListSanPham();
		String response = new Gson().toJson(listSP);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void addHoaDon(HoaDon hd) {
		hoaDonDAO = new HoaDonDAO(em);
		if (hoaDonDAO.addHoaDon(hd) == 0) {
			try {
				PrintWriter writer = new PrintWriter(socket.getOutputStream());
				writer.println("false");
				writer.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				PrintWriter writer = new PrintWriter(socket.getOutputStream());
				writer.println("true");
				writer.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void addCTHD(ChiTietHoaDon cthd) {
		chiTietHoaDonDAO = new ChiTietHoaDonDAO(em);
		if (chiTietHoaDonDAO.addChiTietHoaDon(cthd) == 0) {
			try {
				PrintWriter writer = new PrintWriter(socket.getOutputStream());
				writer.println("false");
				writer.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				PrintWriter writer = new PrintWriter(socket.getOutputStream());
				writer.println("true");
				writer.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void updateSP(SanPham sp) {
		sanPhamDAO = new SanPhamDAO(em);
		if (sanPhamDAO.capNhatSoLuong(sp) == 0) {
			try {
				PrintWriter writer = new PrintWriter(socket.getOutputStream());
				writer.println("false");
				writer.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				PrintWriter writer = new PrintWriter(socket.getOutputStream());
				writer.println("true");
				writer.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void getAllCTHDByIDhd(Scanner sc) {
		chiTietHoaDonDAO = new ChiTietHoaDonDAO(em);
		String id = sc.nextLine();
		List<ChiTietHoaDon> listCTHD = chiTietHoaDonDAO.getCTHDById(id);
		String response = new Gson().toJson(listCTHD);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
