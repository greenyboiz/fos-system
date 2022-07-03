package fpt.edu.capstone.implementService;

import fpt.edu.capstone.entities.Request;

import java.util.List;
import java.util.Optional;

public interface IRequestService {
    public Request addRequest(Request request);

    public Request updateRequest(Request request);

    public boolean deleteRequest(Long id);

    public List<Request> getAllRequest();

    public Optional<Request> getRequestById(Long id);
}
