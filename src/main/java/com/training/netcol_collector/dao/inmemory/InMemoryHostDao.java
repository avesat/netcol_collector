package com.training.netcol_collector.dao.inmemory;

import com.training.netcol_collector.dao.HostDao;
import com.training.netcol_collector.model.Host;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InMemoryHostDao implements HostDao {
    private List<Host> hosts = new ArrayList<>();

    @Override
    public Host save(Host host) {
        hosts.add(host);
        return host;
    }

    /*TODO: implement this method */
    @Override
    public <S extends Host> Iterable<S> saveAll(Iterable<S> newHosts) {
        /*
        hosts.addAll(
                StreamSupport.stream(newHosts.spliterator(), false)
                        .collect(Collectors.toList())
        );
        */
        return null;
    }

    @Override
    public Optional<Host> findById(Long id) {
        return hosts.stream()
                .filter(host -> host.getId() == id)
                .findFirst();
    }

    public Optional<Host> findByRequestId(Long reqId) {
        Optional<Host> h = hosts.stream()
                .filter(host -> host.getRequestId().equals(reqId))
                .findFirst();

        return h;
    }

    @Override
    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    @Override
    public Iterable<Host> findAll() {
        return hosts;
    }

    @Override
    public Iterable<Host> findAllById(Iterable<Long> ids) {
        return hosts.stream()
                .filter(host -> {
                    for (Long id : ids) {
                        if (host.getId() == id) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return hosts.size();
    }

    @Override
    public void deleteById(Long id) {
        for (Iterator<Host> iter = hosts.iterator(); iter.hasNext(); ) {
            Host host = iter.next();
            if (host.getId() == id) {
                iter.remove();
                break;
            }
        }
    }

    @Override
    public void delete(Host host) {
        deleteById(host.getId());
    }

    @Override
    public void deleteAll(Iterable<? extends Host> iterHosts) {
        for (Host host : iterHosts) {
            deleteById(host.getId());
        }
    }

    @Override
    public void deleteAll() {
        hosts.clear();
    }
}
