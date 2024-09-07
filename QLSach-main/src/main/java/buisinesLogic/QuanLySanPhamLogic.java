package buisinesLogic;

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

import dao.NhaCungCapDAO;
import dao.NhaXuatBanDao;
import dao.SachDao;
import dao.SanPhamDAO;
import dao.TheLoaiDao;
import dao.VppDao;
import entity.NhaCungCap;
import entity.NhaXuatBan;
import entity.Sach;
import entity.SanPham;
import entity.TheLoai;
import entity.VPP;
import jakarta.persistence.EntityManager;

public class QuanLySanPhamLogic {
	private Socket socket;
	private EntityManager em;
	private SanPhamDAO sanPhamDAO;
	private SachDao sachDao;
	private VppDao vppDao;
	private NhaCungCapDAO nccDao;
	private TheLoaiDao theLoaiDao;
	private NhaXuatBanDao nxbDao;
	private Sach sach;
	private VPP vpp;

	public QuanLySanPhamLogic(EntityManager em, Socket socket) {
		super();
		this.socket = socket;
		this.em = em;

	}

	public void handleClientRequest(Scanner scanner) {
		String action = scanner.nextLine();
		switch (action) {
		case "getAllSP":
			getAllSP();
			break;
		case "getAllSach":
			getAllSach();
			break;
		case "getAllVPP":
			getAllVPP();
			break;
		case "getVppID":
			getVppID(scanner);
			break;
		case "getSachID":
			getSachID(scanner);
			break;
		case "autoID":
			generateIDsach();
			break;
		case "autoIDVPP":
			generateIDvpp();
			break;
		case "addSach":
			addSach(scanner);
			break;
		case "addVPP":
			addVpp(scanner);
			break;
		case "updateSach":
			updateSach(scanner);
			break;
		case "updateVPP":
			updateVpp(scanner);
			break;
		case "top10SPBanChay":
			top10SPBanChay();
			break;
		case "top10SPBanCham":
			top10SPBanCham();
			break;
		default:
			System.out.println("Unknown action: " + action);
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

	public void getSachID(Scanner sc) {
		sachDao = new SachDao(em);
		String id = sc.nextLine();
		Sach s = sachDao.getSachByID(id);
		String response = new Gson().toJson(s);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void getVppID(Scanner sc) {
		vppDao = new VppDao(em);
		String id = sc.nextLine();
		VPP vpp = vppDao.getVPPByID(id);
		String response = new Gson().toJson(vpp);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void addSach(Scanner scanner) {
		sachDao = new SachDao(em);
		nxbDao = new NhaXuatBanDao(em);
		theLoaiDao = new TheLoaiDao(em);
		nccDao = new NhaCungCapDAO(em);

		String maSP = sachDao.auto_ID();
		String tenSP = scanner.nextLine();
		String tenTG = scanner.nextLine();
		double giaBan = Double.parseDouble(scanner.nextLine());
		String hinhAnh = scanner.nextLine();
		
		String nhaXuatBan = scanner.nextLine();
		NhaXuatBan nxb = new NhaXuatBan();
		nxb = nxbDao.getNhaXuatBanByName(nhaXuatBan);
		
		String stheLoai = scanner.nextLine();
		TheLoai theLoai = new TheLoai();
		theLoai = theLoaiDao.getTheLoaiByName(stheLoai);
		
		String ncc = scanner.nextLine();
		NhaCungCap nhaCungCap = new NhaCungCap();
		nhaCungCap = nccDao.getNhaCungCapByName(ncc);
		
		Sach s = new Sach(maSP, tenSP, nhaCungCap, 0, giaBan, hinhAnh, nxb, theLoai, tenTG);
		if(sachDao.themSach(s) == 1) {
			try {
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				printWriter.println("Add success");
				printWriter.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				printWriter.println("Add fail");
				printWriter.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void addVpp(Scanner scanner) {
		vppDao = new VppDao(em);
		nccDao = new NhaCungCapDAO(em);
		
		String maSP = vppDao.auto_ID();
		
		String tenSP = scanner.nextLine();
		
		String ncc = scanner.nextLine();
		NhaCungCap nhaCungCap = new NhaCungCap();
		nhaCungCap = nccDao.getNhaCungCapByName(ncc);
		
		double donGiaBan = Double.parseDouble(scanner.nextLine());
		
		String hinhAnh = scanner.nextLine();
		
		String xuatXu = scanner.nextLine();
		
		String mauSac = scanner.nextLine();
		
		String chatLieu = scanner.nextLine();
				
		VPP vpp = new VPP(maSP, tenSP, nhaCungCap, 0, donGiaBan, hinhAnh, xuatXu, mauSac, chatLieu);
		
		if (vppDao.addVPP(vpp) == 1) {
			try {
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				printWriter.println("Add success");
				printWriter.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				printWriter.println("Add fail");
				printWriter.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void updateVpp(Scanner scanner) {
		vppDao = new VppDao(em);
		nccDao = new NhaCungCapDAO(em);
		
		String maSP = scanner.nextLine();
		
		String tenSP = scanner.nextLine();
		
		String ncc = scanner.nextLine();
		NhaCungCap nhaCungCap = new NhaCungCap();
		nhaCungCap = nccDao.getNhaCungCapByName(ncc);
		
		double donGiaBan = Double.parseDouble(scanner.nextLine());
		
		String hinhAnh = scanner.nextLine();
		
		String xuatXu = scanner.nextLine();
		
		String mauSac = scanner.nextLine();
		
		String chatLieu = scanner.nextLine();
		
		int soLuong = Integer.parseInt(scanner.nextLine());
				
		VPP vpp = new VPP(maSP, tenSP, nhaCungCap, soLuong, donGiaBan, hinhAnh, xuatXu, mauSac, chatLieu);
		
		if (vppDao.updateVPP(vpp) == 1) {
			try {
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				printWriter.println("Update success");
				printWriter.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				printWriter.println("Update fail");
				printWriter.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void updateSach(Scanner scanner) {
		sachDao = new SachDao(em);
		nxbDao = new NhaXuatBanDao(em);
		theLoaiDao = new TheLoaiDao(em);
		nccDao = new NhaCungCapDAO(em);

		String maSP = scanner.nextLine();
		String tenSP = scanner.nextLine();
		String tenTG = scanner.nextLine();
		double giaBan = Double.parseDouble(scanner.nextLine());
		String hinhAnh = scanner.nextLine();
		
		String nhaXuatBan = scanner.nextLine();
		NhaXuatBan nxb = new NhaXuatBan();
		nxb = nxbDao.getNhaXuatBanByName(nhaXuatBan);
		
		String stheLoai = scanner.nextLine();
		TheLoai theLoai = new TheLoai();
		theLoai = theLoaiDao.getTheLoaiByName(stheLoai);
		
		String ncc = scanner.nextLine();
		NhaCungCap nhaCungCap = new NhaCungCap();
		nhaCungCap = nccDao.getNhaCungCapByName(ncc);
		
		int soLuong = Integer.parseInt(scanner.nextLine());
		
		Sach s = new Sach(maSP, tenSP, nhaCungCap, soLuong, giaBan, hinhAnh, nxb, theLoai, tenTG);
		if(sachDao.capNhat(s) == 1) {
			try {
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				printWriter.println("Update success");
				printWriter.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			try {
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
				printWriter.println("Update fail");
				printWriter.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void generateIDsach() {
		SachDao sachDao = new SachDao(em);
		try {
			PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
			printWriter.println(sachDao.auto_ID());
			printWriter.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

    public void generateIDvpp() {
    	VppDao vppDao = new VppDao(em);
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            printWriter.println(vppDao.auto_ID());
            printWriter.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
	public void getSachTheoTen(Scanner sc) {
		sachDao = new SachDao(em);
		String id = sc.nextLine();
		Sach s = sachDao.timSachTheoTenSach(id);
		String response = new Gson().toJson(s);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void getVppTheoTen(Scanner sc) {
		vppDao = new VppDao(em);
		String id = sc.nextLine();
		VPP vpp = vppDao.timVPPTheoTen(id);
		String response = new Gson().toJson(vpp);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void top10SPBanChay() {
		sanPhamDAO = new SanPhamDAO(em);
		List<SanPham> listSP = sanPhamDAO.top10SanPhamBanChay();
		String response = new Gson().toJson(listSP);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void top10SPBanCham() {
		sanPhamDAO = new SanPhamDAO(em);
		List<SanPham> listSP = sanPhamDAO.top10SanPhamBanCham();
		String response = new Gson().toJson(listSP);
		try {
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(response);
			writer.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
