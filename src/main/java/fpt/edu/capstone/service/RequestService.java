package fpt.edu.capstone.service;

import fpt.edu.capstone.entities.Request;
import fpt.edu.capstone.implementService.IRequestService;
import fpt.edu.capstone.repo.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestService implements IRequestService {
    @Autowired
    private RequestRepository requestRepository;
    @Override
    public Request addRequest(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public Request updateRequest(Request request) {
        if(request != null){
            Request request1 = requestRepository.getById(request.getRequestId());
            if(request1 != null){
                request1.setRequestDetail(request.getRequestDetail());
//                request1.setRequestType(request.getRequestType());

                return requestRepository.save(request1);
            }
        }
        return null;
    }

    @Override
    public boolean deleteRequest(Long id) {
        Request request = requestRepository.getById(id);
        if(request != null){
            requestRepository.delete(request);
            return true;
        }
        return false;
    }

    @Override
    public List<Request> getAllRequest() {
        return requestRepository.findAll();
    }

    @Override
    public Optional<Request> getRequestById(Long id) {
        return Optional.empty();
    }
}
