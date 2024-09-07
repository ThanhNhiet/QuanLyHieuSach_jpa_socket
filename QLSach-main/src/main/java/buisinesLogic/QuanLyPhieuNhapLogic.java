package buisinesLogic;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;

import dao.ChiTietHoaDonDAO;
import dao.ChiTietPhieuNhapDAO;
import dao.HoaDonDAO;
import dao.PhieuNhapDAO;
import dao.SanPhamDAO;
import entity.ChiTietHoaDon;
import entity.ChiTietPhieuNhap;
import entity.HoaDon;
import entity.NhanVien;
import entity.PhieuNhap;
import entity.Sach;
import entity.SanPham;
import entity.VPP;
import jakarta.persistence.EntityManager;

public class QuanLyPhieuNhapLogic {
	private Socket socket;
	private EntityManager em;
	private PhieuNhapDAO phieuNhapDao;
	private ChiTietPhieuNhapDAO chiTietPhieuNhapDao;
	private SanPhamDAO sanPhamDAO;

	public QuanLyPhieuNhapLogic(Socket socket, EntityManager em) {
		this.socket = socket;
		this.em = em;
		this.phieuNhapDao = new PhieuNhapDAO(em);
		this.chiTietPhieuNhapDao = new ChiTietPhieuNhapDAO(em);
	}

	public void handleClientRequest(Scanner scanner) {
		String action = scanner.nextLine();
		switch (action) {
		case "getAllPN":
			getAllPN();
			tinhTongTien();
			break;
		case "getCTPN_id":
			getAllCTPNByID(scanner);
			break;
		case "btnTimKiem":
			getDsPNByDate(scanner, scanner);
			break;
		case "btnXuatPhieuNhap":
			getPNByID_TT(scanner);
			break;
		case "autoIDPhieuNhap":
			auto_IDPhieuNhap();
			break;
		case "thanhToan":
			auto_IDPhieuNhap();
			String pnObject = scanner.nextLine();
			PhieuNhap pn = new Gson().fromJson(pnObject, new TypeToken<PhieuNhap>() {
			}.getType());
			addPN(pn);
			
			try {
				String stringRowJTable_DSSPBan = scanner.nextLine();
				int rowJTable_DSSPBan = Integer.parseInt(stringRowJTable_DSSPBan);
				for (int i = 0; i < rowJTable_DSSPBan; i++) {
					getSPByID(scanner);
					
					String idSP = scanner.nextLine();
					String ctpnObject = scanner.nextLine();
					
					ChiTietPhieuNhap ctpn = null;
					if (idSP.startsWith("S")) {
						InstanceCreator<SanPham> sachInstanceCreator = new InstanceCreator<SanPham>() {
							@Override
							public SanPham createInstance(Type type) {
								return new Sach();
							}
						};
						Gson gsonSach = new GsonBuilder().registerTypeAdapter(SanPham.class, sachInstanceCreator)
								.create();

						ctpn = gsonSach.fromJson(ctpnObject, new TypeToken<ChiTietPhieuNhap>() {
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
						ctpn = gsonVPP.fromJson(ctpnObject, new TypeToken<ChiTietPhieuNhap>() {
						}.getType());
					}

					addCTPN(ctpn);
					
					String idSP2 = scanner.nextLine();
					SanPham sp = null;
					String spObject = scanner.nextLine();
					if (idSP2.startsWith("S")) {
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
					
					updateSLSP(sp);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			getAllCTPNByID_V2(scanner);
			break;
		default:
			System.out.println("Unknown action: " + action);
		}
	}

	public void getAllPN() {
		try {
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.println(new Gson().toJson(phieuNhapDao.getAllPhieuNhap()));
			printWriter.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void tinhTongTien() {
		List<PhieuNhap> list = phieuNhapDao.getAllPhieuNhap();
		double[] listTongTien = new double[list.size()];
		int index = 0;

		for (PhieuNhap pn : list) {
			double tongTien = 0;

			List<ChiTietPhieuNhap> listCTPN_id = chiTietPhieuNhapDao.getCTPNById(pn.getMaPhieuNhap());

			for (ChiTietPhieuNhap ctpn : listCTPN_id) {
				tongTien += ctpn.thanhTien();
			}

			listTongTien[index] = tongTien;
			index++;
		}

		String response = new Gson().toJson(listTongTien);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void getAllCTPNByID(Scanner sc) {
		String id = sc.nextLine();
		List<ChiTietPhieuNhap> listCTPN = chiTietPhieuNhapDao.getCTPNById(id);

		List<ChiTietPhieuNhap> listCTHD_Sach = new ArrayList<>();
		List<ChiTietPhieuNhap> listCTHD_VPP = new ArrayList<>();
		for (ChiTietPhieuNhap ctpn : listCTPN) {
			if (ctpn.getSanPham() instanceof Sach) {
				listCTHD_Sach.add(ctpn);
			} else {
				listCTHD_VPP.add(ctpn);
			}
		}

		String responseSach = new Gson().toJson(listCTHD_Sach);
		String responseVPP = new Gson().toJson(listCTHD_VPP);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(responseSach);
			writer.println(responseVPP);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void getDsPNByDate(Scanner scF, Scanner scE) {
		String from = scF.nextLine();
		String to = scE.nextLine();
		Date ngayBatDau = Date.valueOf(from);
		Date ngayKetThuc = Date.valueOf(to);

		List<PhieuNhap> listPN = phieuNhapDao.getPhieuNhapByDate(ngayBatDau, ngayKetThuc);

		double[] listTongTien = new double[listPN.size()];
		int index = 0;
		for (PhieuNhap pn : listPN) {
			double tongTien = 0;
			List<ChiTietPhieuNhap> listCTPN_id = chiTietPhieuNhapDao.getCTPNById(pn.getMaPhieuNhap());
			for (ChiTietPhieuNhap ctpn : listCTPN_id) {
				tongTien += ctpn.thanhTien();
			}
			listTongTien[index] = tongTien;
			index++;
		}

		String responseHD = new Gson().toJson(listPN);
		String responseTT = new Gson().toJson(listTongTien);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(responseHD);
			writer.println(responseTT);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void getPNByID_TT(Scanner sc) {
		String id = sc.nextLine();
		PhieuNhap pn = phieuNhapDao.getPhieuNhapById(id);

		List<ChiTietPhieuNhap> listCTPN_id = chiTietPhieuNhapDao.getCTPNById(id);

		String responsePN = new Gson().toJson(pn);
		String responseTT = new Gson().toJson(listCTPN_id);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(responsePN);
			writer.println(responseTT);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void auto_IDPhieuNhap() {
		String finalId = phieuNhapDao.auto_IDPhieuNhap();
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(finalId);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void addPN(PhieuNhap pn) {
		if (phieuNhapDao.addPhieuNhap(pn) == -1) {
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
	
	public void addCTPN(ChiTietPhieuNhap ctpn) {
		if (chiTietPhieuNhapDao.addChiTietPhieuNhap(ctpn) == -1) {
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
	
	public void updateSLSP(SanPham sp) {
		if (chiTietPhieuNhapDao.capNhatSoLuong(sp) == -1) {
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
	
	public void getAllCTPNByID_V2(Scanner sc) {
		String id = sc.nextLine();
		List<ChiTietPhieuNhap> listCTPN = chiTietPhieuNhapDao.getCTPNById(id);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(new Gson().toJson(listCTPN));
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
