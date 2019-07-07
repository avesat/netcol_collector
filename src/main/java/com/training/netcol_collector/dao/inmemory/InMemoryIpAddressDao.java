package com.training.netcol_collector.dao.inmemory;

import com.training.netcol_collector.dao.IpAddressDao;
import com.training.netcol_collector.model.IpAddress;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InMemoryIpAddressDao implements IpAddressDao {

    @Override
    public IpAddress save(IpAddress addr) {
        return addr;
    }

    @Override
    public <S extends IpAddress> List<S> saveAll(Iterable<S> addr) {
        return null;
    }

    @Override
    public Optional<IpAddress> findById(Long id) {
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return true;
    }

    @Override
    public Iterable<IpAddress> findAll() {
        return null;
    }

    @Override
    public Iterable<IpAddress> findAllById(Iterable<Long> ids) {
        return null;
    }

    @Override
    public long count() {
        return 0L;
    }

    @Override
    public void deleteById(Long id) { }

    @Override
    public void delete(IpAddress addr) { }

    @Override
    public void deleteAll(Iterable<? extends IpAddress> addrs) { }

    @Override
    public void deleteAll() { }
}
