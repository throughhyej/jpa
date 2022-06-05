package jpa.basic;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {

        // 1. 설정 정보 조회
        // 2. entitymanagerfactory 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-persistence");
        // 3. 단위행위마다 entitymanager 생성
        EntityManager em = emf.createEntityManager();
        // 4. transaction 단위에서 실행
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            /* 생성 */
            Member member = new Member();
            member.setId(1L);
            member.setName("member");
            em.persist(member);

            /* 조회 */
            Member findMember = em.find(Member.class, 1L);
            System.out.println("### findMember => " + findMember.getName());

            /* 수정 */
            findMember.setName("update_member");
            System.out.println("### update name => " + findMember.getName());

            /* 삭제 */
            em.remove(findMember);

            /* 검색 조건 JPQL */
            em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            transaction.commit();
        } catch (Exception err) {
            transaction.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}