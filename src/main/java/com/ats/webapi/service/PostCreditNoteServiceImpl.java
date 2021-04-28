package com.ats.webapi.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ats.webapi.model.bill.Company;
import com.ats.webapi.model.grngvn.PostCreditNoteDetails;
import com.ats.webapi.model.grngvn.PostCreditNoteHeader;
import com.ats.webapi.repository.CompanyRepository;
import com.ats.webapi.repository.FrItemStockConfigureRepository;
import com.ats.webapi.repository.PostCreditNoteDetailsRepository;
import com.ats.webapi.repository.PostCreditNoteHeaderRepository;
import com.ats.webapi.repository.UpdateGrnGvnForCreditNoteRepository;
import com.ats.webapi.repository.UpdateSeetingForPBRepo;
import com.ats.webapi.repository.grngvnheader.UpdateGrnGvnHeaderForCNRepo;

@Service
public class PostCreditNoteServiceImpl implements PostCreditNoteService {

	@Autowired
	PostCreditNoteHeaderRepository postCreditNoteHeaderRepository;

	@Autowired
	PostCreditNoteDetailsRepository postCreditNoteDetailsRepository;
	
	@Autowired
	UpdateGrnGvnForCreditNoteRepository updateGrnGvnForCreditNoteRepository;
	
	
	@Autowired
	UpdateGrnGvnHeaderForCNRepo updateGrnGvnHeaderForCNRepo;
	
	@Autowired
	FrItemStockConfigureRepository frItemStockConfRepo;
	
	@Autowired
	UpdateSeetingForPBRepo updateSeetingForPBRepo;
	@Autowired CompanyRepository companyRepository;
	@Override
	public List<PostCreditNoteHeader> savePostCreditNote(List<PostCreditNoteHeader> postCreditNoteHeader) {

		PostCreditNoteHeader creditNoteHeader = null;

		List<PostCreditNoteHeader> postCreditNoteHeaderList = new ArrayList<PostCreditNoteHeader>();
		for (int i = 0; i < postCreditNoteHeader.size(); i++) {

			creditNoteHeader = new PostCreditNoteHeader();
			int crnSrNo=frItemStockConfRepo.findBySettingKey("CRE_NOTE_NO");
			
			
			Company company = null;
			try {
				String pattern = "yyyy-MM-dd";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

				String date = simpleDateFormat.format(new java.util.Date());
				 company=companyRepository.findByBillDate(date);

			} catch (Exception e) {
				System.out.println(" Exce in bill Header List for Print " + e.getMessage());
				e.printStackTrace();
			}
			
			
			//System.err.println("crnSrNo"+crnSrNo);
			String credNotePrefix=null;
			credNotePrefix=company.getExVar6();
			//String strCrnNo = String.format("%03d", crnSrNo);

			postCreditNoteHeader.get(i).setCrnNo(credNotePrefix+crnSrNo);

			creditNoteHeader = postCreditNoteHeaderRepository.save(postCreditNoteHeader.get(i));

			if(creditNoteHeader.getCrnId()!=0) {
				/*	
					int result= updateGrnGvnForCreditNoteRepository.updateGrnGvnForCreditNoteInsert(
							creditNoteHeader.getGrnGvnId(), 1);*/
					System.err.println("crnSrNo  while update " +crnSrNo);
					int result = updateSeetingForPBRepo.updateSeetingForPurBill(crnSrNo+1, "CRE_NOTE_NO");
					
				}

			postCreditNoteHeaderList.add(creditNoteHeader);
			
			int res=0;
			

			int crnId = creditNoteHeader.getCrnId();

			List<PostCreditNoteDetails> postCreditNoteDetailsList = postCreditNoteHeader.get(i)
					.getPostCreditNoteDetails();

			for (int j = 0; j < postCreditNoteDetailsList.size(); j++) {

				PostCreditNoteDetails postCreditNoteDetails = postCreditNoteDetailsList.get(j);

				postCreditNoteDetails.setCrnId(crnId);
				
				postCreditNoteDetailsRepository.save(postCreditNoteDetails);
				
				int result= updateGrnGvnForCreditNoteRepository.updateGrnGvnForCreditNoteInsert(
						postCreditNoteDetails.getGrnGvnId(), 1);
				
				int isCrnNoPresent=0;
				try {
				isCrnNoPresent=updateGrnGvnHeaderForCNRepo.isCrnNoPresent(crnSrNo,postCreditNoteDetails.getGrnGvnHeaderId());
				}
				catch (Exception e) {
					isCrnNoPresent=0;
				}
				if(isCrnNoPresent!=0)
				{
				res=updateGrnGvnHeaderForCNRepo.updateGrnGvnHeaderForCN(crnSrNo, 1, postCreditNoteDetails.getGrnGvnHeaderId());
				}
			}
		}

		return postCreditNoteHeaderList;
	}

	@Override
	public List<PostCreditNoteHeader> postCreditNoteForUpdate(List<PostCreditNoteHeader> postCreditNoteHeader) {

		PostCreditNoteHeader creditNoteHeader = null;

		List<PostCreditNoteHeader> postCreditNoteHeaderList = new ArrayList<PostCreditNoteHeader>();
		for (int i = 0; i < postCreditNoteHeader.size(); i++) {

			creditNoteHeader = new PostCreditNoteHeader();
	
			creditNoteHeader = postCreditNoteHeaderRepository.save(postCreditNoteHeader.get(i));

			postCreditNoteHeaderList.add(creditNoteHeader);
			
			int crnId = creditNoteHeader.getCrnId();

			List<PostCreditNoteDetails> postCreditNoteDetailsList = postCreditNoteHeader.get(i)
					.getPostCreditNoteDetails();

			for (int j = 0; j < postCreditNoteDetailsList.size(); j++) {

				PostCreditNoteDetails postCreditNoteDetails = postCreditNoteDetailsList.get(j);

				postCreditNoteDetails.setCrnId(crnId);
				
				postCreditNoteDetailsRepository.save(postCreditNoteDetails);
				
			}
		}

		return postCreditNoteHeaderList;
	}

}
