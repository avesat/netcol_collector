package com.training.netcol_collector.dao.inmemory;

import com.training.netcol_collector.dao.IpInterfaceDao;
import com.training.netcol_collector.model.IpInterface;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InMemoryIpInterfaceDao implements IpInterfaceDao {

    @Override
    public IpInterface save(IpInterface intf) {
        return intf;
    }

    @Override
    public <S extends IpInterface> List<S> saveAll(Iterable<S> intf) {
        return null;
    }

    @Override
    public Optional<IpInterface> findById(Long id) {
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return true;
    }

    @Override
    public Iterable<IpInterface> findAll() {
        return null;
    }

    @Override
    public Iterable<IpInterface> findAllById(Iterable<Long> ids) {
        return null;
    }

    @Override
    public long count() {
        return 0L;
    }

    @Override
    public void deleteById(Long id) { }

    @Override
    public void delete(IpInterface intf) { }

    @Override
    public void deleteAll(Iterable<? extends IpInterface> intfs) { }

    @Override
    public void deleteAll() { }
}
