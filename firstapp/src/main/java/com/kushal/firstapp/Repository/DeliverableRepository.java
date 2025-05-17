package com.kushal.firstapp.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kushal.firstapp.Model.Deliverable;
import com.kushal.firstapp.Model.Deliverable.Status;

public interface DeliverableRepository extends JpaRepository<Deliverable, Long> {
    List<Deliverable> findByProposal_ProposalId(Long proposalId);




    List<Deliverable> findByProposal_ProposalIdInAndStatus(List<Long> proposalIds, Status status);
List<Deliverable> findByProposal_ProposalIdInAndStatusNot(List<Long> proposalIds, Status status);

    

}

